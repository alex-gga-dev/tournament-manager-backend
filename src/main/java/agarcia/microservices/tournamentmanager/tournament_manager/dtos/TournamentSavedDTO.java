package agarcia.microservices.tournamentmanager.tournament_manager.dtos;

import java.time.LocalDate;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.enums.TournamentStatus;

public record TournamentSavedDTO(
        String name,
        LocalDate startDate,
        LocalDate endDate,
        TournamentStatus status) {

}