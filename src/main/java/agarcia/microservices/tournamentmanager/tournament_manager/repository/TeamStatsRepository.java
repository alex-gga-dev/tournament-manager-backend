package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamStats;

public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {

}
