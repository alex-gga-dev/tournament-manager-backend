package agarcia.microservices.tournamentmanager.tournament_manager.dtos;

public record TeamStatsDTO(
        TeamDTO team,
        int goalsFor,
        int goalsAgainst,
        int wins,
        int losses,
        int draws) {

}
