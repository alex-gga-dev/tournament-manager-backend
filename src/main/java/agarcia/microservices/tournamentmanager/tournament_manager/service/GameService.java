package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    public List<Game> getBetweenDates(LocalDate startDate, LocalDate enDate) {
        return gameRepository.findGamesBetweenDates(startDate, enDate);
    }

    public List<Game> getByTeamName(String name) {
        return gameRepository.findGamesByTeamName(name);
    }

}
