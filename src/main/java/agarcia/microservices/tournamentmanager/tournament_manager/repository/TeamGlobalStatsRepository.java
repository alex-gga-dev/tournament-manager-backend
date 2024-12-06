package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamGlobalStats;

public interface TeamGlobalStatsRepository extends JpaRepository<TeamGlobalStats, Long> {

    Optional<TeamGlobalStats> findByTeam(Team team);

}
