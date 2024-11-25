package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByFirstName(String name);

    @Query("SELECT p FROM Player p WHERE p.firstName = :firstName AND p.lastName = :lastName AND p.team.id = :teamId")
    Optional<Player> findByFullNameAndTeam(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("teamId") Long teamId);

}