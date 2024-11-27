package agarcia.microservices.tournamentmanager.tournament_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamGlobalStats;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamGlobalStatsRepository;

@Service
public class TeamGlobalStatsService {

    @Autowired
    private TeamGlobalStatsRepository teamGlobalStatsRepository;

    public TeamGlobalStats save(TeamGlobalStats teamGlobalStats) {
        return teamGlobalStatsRepository.save(teamGlobalStats);
    }

}
