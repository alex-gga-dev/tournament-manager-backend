package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.GameDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;
import agarcia.microservices.tournamentmanager.tournament_manager.mapper.GameMapper;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.GameRepository;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final TeamStatsService teamStatsService;
    private final TeamGlobalStatsService teamGlobalStatsService;
    private final GameMapper gameMapper;

    public GameService(
            GameRepository gameRepository,
            GameMapper gameMapper,
            TeamStatsService teamStatsService,
            TeamGlobalStatsService teamGlobalStatsService) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.teamStatsService = teamStatsService;
        this.teamGlobalStatsService = teamGlobalStatsService;

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

        Game game = gameMapper.mapToEntity(gameDTO);

        gameRepository.save(game);

        teamStatsService.updateTeamStats(game);
        teamGlobalStatsService.updateGlobalTeamStats(game);

        return gameDTO;
    }

}
