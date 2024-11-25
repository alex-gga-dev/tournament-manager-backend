package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.PlayerStats;

public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {

}
