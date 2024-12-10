package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamStats;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamStatsRepository;
import jakarta.persistence.EntityNotFoundException;

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

    public void updateTeamStats(Game game) {
        Tournament tournament = game.getTournament();
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();

        System.out.println("Home team: " + homeTeam + ", Away team: " + awayTeam);

        TeamStats homeStats = teamStatsRepository.findByTeamAndTournament(homeTeam, tournament).orElseThrow(
                () -> new EntityNotFoundException(
                        "Home Team: " + homeTeam.getName() + " does not exist in this Tournament."));

        TeamStats awayStats = teamStatsRepository.findByTeamAndTournament(awayTeam, tournament).orElseThrow(
                () -> new EntityNotFoundException(
                        "Away Team: " + awayTeam.getName() + " does not exist in this Tournament."));

        int homeGoals = game.getHomeGoals();
        int awayGoals = game.getAwayGoals();

        homeStats.setGoalsFor(homeStats.getGoalsFor() + homeGoals);
        homeStats.setGoalsAgainst(homeStats.getGoalsAgainst() + awayGoals);

        awayStats.setGoalsFor(awayStats.getGoalsFor() + awayGoals);
        awayStats.setGoalsAgainst(awayStats.getGoalsAgainst() + homeGoals);

        if (homeGoals > awayGoals) {
            homeStats.setWins(homeStats.getWins() + 1);
            awayStats.setLosses(awayStats.getLosses() + 1);
        } else if (awayGoals > homeGoals) {
            awayStats.setWins(awayStats.getWins() + 1);
            homeStats.setLosses(homeStats.getLosses() + 1);
        } else {
            homeStats.setDraws(homeStats.getDraws() + 1);
            awayStats.setDraws(awayStats.getDraws() + 1);
        }

        teamStatsRepository.save(homeStats);
        teamStatsRepository.save(awayStats);

    }

}
