package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Player;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.enums.Position;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE " +
            "(:firstName IS NULL OR p.firstName LIKE %:firstName%) AND " +
            "(:lastName IS NULL OR p.lastName LIKE %:lastName%) AND " +
            "(:position IS NULL OR p.position = :position) AND " +
            "(:teamId IS NULL OR p.team.id = :teamId)")
    List<Player> findByFilter(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("position") Position position,
            @Param("teamId") Long teamId);

    @Query("SELECT p FROM Player p WHERE p.firstName = :firstName AND p.lastName = :lastName AND p.team.id = :teamId")
    Optional<Player> findByFullNameAndTeam(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("teamId") Long teamId);

}