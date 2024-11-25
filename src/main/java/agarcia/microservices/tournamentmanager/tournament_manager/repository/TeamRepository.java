package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
