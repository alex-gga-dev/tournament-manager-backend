package agarcia.microservices.tournamentmanager.tournament_manager.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TournamentDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;
import agarcia.microservices.tournamentmanager.tournament_manager.service.TournamentService;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody TournamentDTO tournamentDTO) {
        TournamentDTO createdTournament = tournamentService.save(tournamentDTO);
        return ResponseEntity.ok(createdTournament);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Tournament> tournamentOptional = tournamentService.findById(id);

        if (tournamentOptional.isPresent()) {
            return ResponseEntity.ok(tournamentOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tournament with ID: " + id + " not found.");
        }
    }

    @GetMapping("/filters")
    public ResponseEntity<?> findByFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Tournament> tournaments = tournamentService.findByFilter(name, startDate, endDate);
        ResponseEntity<?> result = (tournaments == null || tournaments.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tournaments));
        return result;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<Tournament> tournaments = tournamentService.findAll();
        ResponseEntity<?> result = (tournaments == null || tournaments.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tournaments));
        return result;
    }

}
