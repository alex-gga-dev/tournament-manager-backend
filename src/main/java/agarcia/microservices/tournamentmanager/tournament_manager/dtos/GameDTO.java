package agarcia.microservices.tournamentmanager.tournament_manager.dtos;

import java.time.LocalDate;

public record GameDTO(
        Long tournamentId,
        Long homeTeamId,
        Long awayTeamId,
        LocalDate date,
        String location,
        int homeGoals,
        int awayGoals) {
}
