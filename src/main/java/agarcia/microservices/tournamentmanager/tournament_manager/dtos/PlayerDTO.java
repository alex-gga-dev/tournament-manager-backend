package agarcia.microservices.tournamentmanager.tournament_manager.dtos;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.enums.Position;
import agarcia.microservices.tournamentmanager.tournament_manager.validation.ValidPlayerName;

public record PlayerDTO(
        @ValidPlayerName String firstName,
        @ValidPlayerName String lastName,
        Position position,
        Long idTeam) {
}
