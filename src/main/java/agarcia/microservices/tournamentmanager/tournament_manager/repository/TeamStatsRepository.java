package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamStats;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;

public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {

    Optional<TeamStats> findByTeamAndTournament(Team team, Tournament tournament);

    List<TeamStats> findByTournament(Tournament tournament);

    boolean existsByTeamAndTournament(Team team, Tournament tournament);

}
