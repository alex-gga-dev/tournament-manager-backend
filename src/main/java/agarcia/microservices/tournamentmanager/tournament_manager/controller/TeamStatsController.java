package agarcia.microservices.tournamentmanager.tournament_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamStats;
import agarcia.microservices.tournamentmanager.tournament_manager.service.TeamStatsService;

@RestController
@RequestMapping("/teams-stats")
public class TeamStatsController {

    @Autowired
    private TeamStatsService teamStatsService;

    @GetMapping("/tournament/{idTournament}")
    public ResponseEntity<?> findByTournament(@PathVariable Long idTournament) {
        List<TeamStats> teamsStats = teamStatsService.findByTournament(idTournament);
        ResponseEntity<?> result = (teamsStats == null || teamsStats.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(teamsStats));
        return result;
    }

}
