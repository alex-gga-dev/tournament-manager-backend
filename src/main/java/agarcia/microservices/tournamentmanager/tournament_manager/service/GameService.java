package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.GameDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamGlobalStats;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamStats;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.GameRepository;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamGlobalStatsRepository;
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
    private final TeamGlobalStatsRepository teamGlobalStatsRepository;

    public GameService(
            GameRepository gameRepository,
            TournamentRepository tournamentRepository,
            TeamRepository teamRepository,
            TeamStatsRepository teamStatsRepository,
            TeamGlobalStatsRepository teamGlobalStatsRepository) {
        this.gameRepository = gameRepository;
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.teamStatsRepository = teamStatsRepository;
        this.teamGlobalStatsRepository = teamGlobalStatsRepository;
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

    @Transactional(readOnly = false, timeout = 60, rollbackFor = Exception.class)
    public GameDTO save(GameDTO gameDTO) {

        Game game = mapToGameEntity(gameDTO);

        gameRepository.save(game);

        updateTeamStats(game);
        updateGlobalTeamStats(game);

        return gameDTO;
    }

    private void updateTeamStats(Game game) {
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

    private void updateGlobalTeamStats(Game game) {
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

    public Game mapToGameEntity(GameDTO gameDTO) {
        Game game = new Game();
        System.out.println(gameDTO);

        Tournament tournament = tournamentRepository.findById(gameDTO.tournamentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tournament with ID " + gameDTO.tournamentId() + " does not exist."));

        Team homeTeam = teamRepository.findById(gameDTO.homeTeamId()).orElseThrow(
                () -> new EntityNotFoundException("Home Team with ID " + gameDTO.homeTeamId() + " does not exist."));

        Team awayTeam = teamRepository.findById(gameDTO.awayTeamId()).orElseThrow(
                () -> new EntityNotFoundException("Away Team with ID " + gameDTO.awayTeamId() + " does not exist."));

        game.setTournament(tournament);
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);
        game.setHomeGoals(gameDTO.homeGoals());
        game.setAwayGoals(gameDTO.awayGoals());
        game.setDate(gameDTO.date());
        game.setLocation(gameDTO.location());

        return game;

    }

}
