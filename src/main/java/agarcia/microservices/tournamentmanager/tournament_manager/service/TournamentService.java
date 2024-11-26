package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TournamentDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TournamentRepository;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    public TournamentDTO save(TournamentDTO tournamentDTO) {

        Tournament tournament = mapToTournamentEntity(tournamentDTO);

        tournamentRepository.save(tournament);

        return tournamentDTO;

    }

    public Optional<Tournament> findById(Long id) {
        return tournamentRepository.findById(id);
    }

    public List<Tournament> findByName(String name) {
        return tournamentRepository.findByName(name);
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public List<Tournament> findByFilter(String name, LocalDate startDate, LocalDate endDate) {
        return tournamentRepository.findByFilter(name, startDate, endDate);
    }

    public Tournament mapToTournamentEntity(TournamentDTO tournamentDTO) {
        Tournament tournament = new Tournament();
        tournament.setName(tournamentDTO.name());
        tournament.setStartDate(tournamentDTO.startDate());
        tournament.setEndDate(tournamentDTO.endDate());
        return tournament;
    }
}
