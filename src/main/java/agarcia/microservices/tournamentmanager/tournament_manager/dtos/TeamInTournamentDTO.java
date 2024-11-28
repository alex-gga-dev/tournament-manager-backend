package agarcia.microservices.tournamentmanager.tournament_manager.dtos;

import java.time.LocalDate;

public record TeamInTournamentDTO(
        String teamName,
        String tournamentName,
        LocalDate startDate,
        LocalDate endDate) {

}
