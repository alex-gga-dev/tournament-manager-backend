package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TeamDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamGlobalStats;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamRepository;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamGlobalStatsService teamGlobalStatsService;

    public TeamService(TeamRepository teamRepository, TeamGlobalStatsService teamGlobalStatsService) {
        this.teamRepository = teamRepository;
        this.teamGlobalStatsService = teamGlobalStatsService;
    }

    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    @Transactional(readOnly = false, timeout = 60, rollbackFor = Exception.class)
    public TeamDTO save(TeamDTO teamDTO) {

        Team team = mapToTeamEntity(teamDTO);
        TeamGlobalStats teamGlobalStats = newTeamGlobalStats(team);

        teamGlobalStatsService.save(teamGlobalStats);
        teamRepository.save(team);

        return teamDTO;
    }

    public Team mapToTeamEntity(TeamDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.name());
        team.setTeamShield(teamDTO.teamShield());
        team.setDescription(teamDTO.description());

        return team;
    }

    public TeamGlobalStats newTeamGlobalStats(Team team) {
        TeamGlobalStats teamGlobalStats = new TeamGlobalStats();
        teamGlobalStats.setTeam(team);
        teamGlobalStats.setGlobalWins(0);
        teamGlobalStats.setGlobalLosses(0);
        teamGlobalStats.setGlobalDraws(0);
        teamGlobalStats.setGlobalGoalsFor(0);
        teamGlobalStats.setGlobalGoalsAgainst(0);
        return teamGlobalStats;
    }
}
