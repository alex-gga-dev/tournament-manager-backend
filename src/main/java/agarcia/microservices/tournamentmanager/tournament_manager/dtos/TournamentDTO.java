package agarcia.microservices.tournamentmanager.tournament_manager.dtos;

import java.time.LocalDate;
import java.util.List;

public record TournamentDTO(
        String name,
        LocalDate startDate,
        LocalDate endDate,
        List<TeamStatsDTO> teamStats) {

}
