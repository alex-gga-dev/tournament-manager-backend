package agarcia.microservices.tournamentmanager.tournament_manager.dtos;

import java.time.LocalDate;

public record TournamentDTO(String name, LocalDate startDate, LocalDate endDate) {

}
