package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TeamDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    public TeamDTO save(TeamDTO teamDTO) {

        Team team = mapToTeamEntity(teamDTO);

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
}
