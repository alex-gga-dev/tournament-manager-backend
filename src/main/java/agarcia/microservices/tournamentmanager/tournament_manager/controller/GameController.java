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

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.GameDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;
import agarcia.microservices.tournamentmanager.tournament_manager.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Game> gameOptional = gameService.findById(id);

        if (gameOptional.isPresent()) {
            return ResponseEntity.ok(gameOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game with ID" + id + " not found");
        }
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody GameDTO gameDTO) {
        GameDTO createdGame = gameService.save(gameDTO);
        return ResponseEntity.ok(createdGame);
    }

    @GetMapping("/dates")
    public ResponseEntity<?> getBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Game> games = gameService.getBetweenDates(startDate, endDate);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/team-name/{teamName}")
    public ResponseEntity<?> getByTeamName(@PathVariable String teamName) {
        List<Game> games = gameService.getByTeamName(teamName);
        return ResponseEntity.ok(games);
    }
}
