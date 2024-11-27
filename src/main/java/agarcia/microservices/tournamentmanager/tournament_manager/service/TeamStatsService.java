package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamStats;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamStatsRepository;

@Service
public class TeamStatsService {

    @Autowired
    private TeamStatsRepository teamStatsRepository;

    public List<TeamStats> findByTournament(Long tournamentId) {
        Tournament tournament = new Tournament();
        tournament.setId(tournamentId);
        List<TeamStats> teamStats = teamStatsRepository.findByTournament(tournament);
        return teamStats;

    }

}
