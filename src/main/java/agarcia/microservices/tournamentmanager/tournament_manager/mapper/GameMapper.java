package agarcia.microservices.tournamentmanager.tournament_manager.mapper;

import org.springframework.stereotype.Component;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.GameDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamRepository;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TournamentRepository;
import jakarta.persistence.EntityNotFoundException;

@Component
public class GameMapper {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    public GameMapper(TournamentRepository tournamentRepository, TeamRepository teamRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
    }

    public Game mapToEntity(GameDTO gameDTO) {
        Game game = new Game();

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

    public GameDTO mapToDTO(Game game) {
        return new GameDTO(game.getTournament().getId(),
                game.getHomeTeam().getId(),
                game.getAwayTeam().getId(),
                game.getDate(),
                game.getLocation(),
                game.getHomeGoals(),
                game.getAwayGoals());
    }

}
