package agarcia.microservices.tournamentmanager.tournament_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;

import java.time.LocalDate;
import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    List<Tournament> findByName(String name);

    @Query("SELECT t FROM Tournament t WHERE  " +
            "(:name IS NULL OR t.name LIKE %:name%) AND  " +
            "(:startDate IS NULL OR t.startDate = :startDate) AND  " +
            "(:endDate IS NULL OR t.endDate = :endDate)")
    List<Tournament> findByFilter(
            @Param("name") String name,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
