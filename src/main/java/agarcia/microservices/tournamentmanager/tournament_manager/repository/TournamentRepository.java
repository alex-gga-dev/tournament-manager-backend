package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

}
