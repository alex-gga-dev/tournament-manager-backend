package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.GameDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamStats;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.GameRepository;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamRepository;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamStatsRepository;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TournamentRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final TeamStatsRepository teamStatsRepository;

    public GameService(
            GameRepository gameRepository,
            TournamentRepository tournamentRepository,
            TeamRepository teamRepository,
            TeamStatsRepository teamStatsRepository) {
        this.gameRepository = gameRepository;
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.teamStatsRepository = teamStatsRepository;
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    public List<Game> getBetweenDates(LocalDate startDate, LocalDate enDate) {
        return gameRepository.findGamesBetweenDates(startDate, enDate);
    }

    public List<Game> getByTeamName(String name) {
        return gameRepository.findGamesByTeamName(name);
    }

    public GameDTO save(GameDTO gameDTO) {

        Game game = mapToGameEntity(gameDTO);

        gameRepository.save(game);

        updateTeamStats(game);

        return gameDTO;
    }

    private void updateTeamStats(Game game) {
        Tournament tournament = game.getTournament();
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();

        TeamStats homeStats = teamStatsRepository.findByTeamAndTournament(homeTeam, tournament);
        TeamStats awayStats = teamStatsRepository.findByTeamAndTournament(awayTeam, tournament);

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
            awayStats.setDraws(homeStats.getDraws() + 1);
        }

        teamStatsRepository.save(homeStats);
        teamStatsRepository.save(awayStats);

    }

    public Game mapToGameEntity(GameDTO gameDTO) {
        Game game = new Game();
        System.out.println(gameDTO.tournamentId());

        Tournament tournament = tournamentRepository.findById(gameDTO.tournamentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tournament with ID " + gameDTO.tournamentId() + " does not exist."));

        Team homeTeam = teamRepository.findById(gameDTO.homeTeamId()).orElseThrow(
                () -> new EntityNotFoundException("Home Team with ID " + gameDTO.homeTeamId() + " does not exist."));

        Team awayTeam = teamRepository.findById(gameDTO.homeTeamId()).orElseThrow(
                () -> new EntityNotFoundException("Away Team with ID " + gameDTO.awayTeamId() + " does not exist."));

        game.setTournament(tournament);
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);
        game.setHomeGoals(gameDTO.homeGoals());
        game.setHomeGoals(gameDTO.awayGoals());
        game.setDate(gameDTO.date());
        game.setLocation(gameDTO.location());

        return game;

    }

}
