package agarcia.microservices.tournamentmanager.tournament_manager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.PlayerDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Player;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.enums.Position;
import agarcia.microservices.tournamentmanager.tournament_manager.service.PlayerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Player> playerOptional = playerService.findById(id);

        if (playerOptional.isPresent()) {
            return ResponseEntity.ok(playerOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player with ID" + id + " not found");
        }
    }

    @PostMapping()
    public ResponseEntity<?> save(@Valid @RequestBody PlayerDTO playerDto) {
        PlayerDTO createdPlayer = playerService.save(playerDto);
        return ResponseEntity.ok(createdPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Player player) {
        PlayerDTO updatePlayer = playerService.update(id, player);
        return ResponseEntity.ok(updatePlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filters")
    public ResponseEntity<?> findByFilter(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Position position,
            @RequestParam(required = false) Long teamId) {
        List<Player> players = playerService.findByFilter(firstName, lastName, position, teamId);
        ResponseEntity<?> result = (players == null || players.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(players));
        return result;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<Player> players = playerService.getPlayers();
        ResponseEntity<?> result = (players == null || players.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(players));
        return result;
    }

}
