package agarcia.microservices.tournamentmanager.tournament_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamGlobalStats;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamGlobalStatsRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TeamGlobalStatsService {

    @Autowired
    private TeamGlobalStatsRepository teamGlobalStatsRepository;

    public TeamGlobalStats save(TeamGlobalStats teamGlobalStats) {
        return teamGlobalStatsRepository.save(teamGlobalStats);
    }

    public void updateGlobalTeamStats(Game game) {
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();

        TeamGlobalStats homeGlobalStats = teamGlobalStatsRepository.findByTeam(homeTeam).orElseThrow(
                () -> new EntityNotFoundException(
                        "Home Team: " + homeTeam.getName() + " does not exist in this Tournament."));

        TeamGlobalStats awayGlobalStats = teamGlobalStatsRepository.findByTeam(awayTeam).orElseThrow(
                () -> new EntityNotFoundException(
                        "Away Team: " + awayTeam.getName() + " does not exist in this Tournament."));

        int homeGoals = game.getHomeGoals();
        int awayGoals = game.getAwayGoals();

        homeGlobalStats.setGlobalGoalsFor(homeGlobalStats.getGlobalGoalsFor() + homeGoals);
        homeGlobalStats.setGlobalGoalsAgainst(homeGlobalStats.getGlobalGoalsAgainst() + awayGoals);

        awayGlobalStats.setGlobalGoalsFor(awayGlobalStats.getGlobalGoalsFor() + awayGoals);
        awayGlobalStats.setGlobalGoalsAgainst(awayGlobalStats.getGlobalGoalsAgainst() + homeGoals);

        if (homeGoals > awayGoals) {
            homeGlobalStats.setGlobalWins(homeGlobalStats.getGlobalWins() + 1);
            awayGlobalStats.setGlobalLosses(awayGlobalStats.getGlobalLosses() + 1);
        } else if (awayGoals > homeGoals) {
            awayGlobalStats.setGlobalWins(awayGlobalStats.getGlobalWins() + 1);
            homeGlobalStats.setGlobalLosses(homeGlobalStats.getGlobalLosses() + 1);
        } else {
            homeGlobalStats.setGlobalDraws(homeGlobalStats.getGlobalDraws() + 1);
            awayGlobalStats.setGlobalDraws(awayGlobalStats.getGlobalDraws() + 1);
        }

        teamGlobalStatsRepository.save(homeGlobalStats);
        teamGlobalStatsRepository.save(awayGlobalStats);

    }

}
