package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.date BETWEEN :startDate AND :endDate")
    List<Game> findGamesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT g FROM Game g " +
            "JOIN Team homeTeam ON g.homeTeam.id = homeTeam.id " +
            "JOIN Team awayTeam ON g.awayTeam.id = awayTeam.id " +
            "WHERE homeTeam.name = :teamName OR awayTeam.name = :teamName")
    List<Game> findGamesByTeamName(@Param("teamName") String teamName);
}
