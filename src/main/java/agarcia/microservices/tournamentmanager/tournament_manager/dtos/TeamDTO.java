package agarcia.microservices.tournamentmanager.tournament_manager.dtos;

import agarcia.microservices.tournamentmanager.tournament_manager.validation.ValidTeamName;

public record TeamDTO(@ValidTeamName String name,
                String teamShield,
                String description) {

}
