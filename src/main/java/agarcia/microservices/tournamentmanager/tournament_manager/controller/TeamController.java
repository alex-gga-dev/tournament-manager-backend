package agarcia.microservices.tournamentmanager.tournament_manager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TeamDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.service.TeamService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Optional<Team> teamOptional = teamService.findById(id);
        return ResponseEntity.ok(teamOptional);
    }

    @PostMapping()
    public ResponseEntity<?> save(@Valid @RequestBody TeamDTO teamDTO) {
        TeamDTO createdTeam = teamService.save(teamDTO);
        return ResponseEntity.ok(createdTeam);
    }

}
