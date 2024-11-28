package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TeamDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TeamInTournamentDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TournamentDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.dtos.TeamStatsDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.TeamStats;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Tournament;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamRepository;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TeamStatsRepository;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.TournamentRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final TeamStatsRepository teamStatsRepository;

    public TournamentService(
            TournamentRepository tournamentRepository,
            TeamRepository teamRepository,
            TeamStatsRepository teamStatsRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.teamStatsRepository = teamStatsRepository;
    }

    public TournamentDTO save(TournamentDTO tournamentDTO) {

        Tournament tournament = mapToTournamentEntity(tournamentDTO);

        tournamentRepository.save(tournament);

        return tournamentDTO;

    }

    public Optional<Tournament> findById(Long id) {
        return tournamentRepository.findById(id);
    }

    public List<Tournament> findByName(String name) {
        return tournamentRepository.findByName(name);
    }

    public List<TournamentDTO> findAll() {
        List<Tournament> tournaments = tournamentRepository.findAll();
        return tournaments.stream().map(this::toTournamentDTO).toList();

    }

    public List<TournamentDTO> findByFilter(String name, LocalDate startDate, LocalDate endDate) {
        List<Tournament> tournaments = tournamentRepository.findByFilter(name, startDate, endDate);
        return tournaments.stream().map(this::toTournamentDTO).toList();
    }

    private TournamentDTO toTournamentDTO(Tournament tournament) {
        List<TeamStatsDTO> teamStats = tournament.getTeamStats().stream().map(this::toTeamStatsDTO).toList();

        return new TournamentDTO(tournament.getName(), tournament.getStartDate(), tournament.getEndDate(), teamStats);

    }

    private TeamStatsDTO toTeamStatsDTO(TeamStats teamStats) {
        TeamDTO team = new TeamDTO(teamStats.getTeam().getName(), teamStats.getTeam().getTeamShield(),
                teamStats.getTeam().getDescription());

        return new TeamStatsDTO(
                team,
                teamStats.getGoalsFor(),
                teamStats.getGoalsAgainst(),
                teamStats.getWins(),
                teamStats.getLosses(),
                teamStats.getDraws()

        );

    }

    public Tournament mapToTournamentEntity(TournamentDTO tournamentDTO) {
        Tournament tournament = new Tournament();
        tournament.setName(tournamentDTO.name());
        tournament.setStartDate(tournamentDTO.startDate());
        tournament.setEndDate(tournamentDTO.endDate());
        return tournament;
    }

    public TeamInTournamentDTO addTeamToTournament(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(
                () -> new EntityNotFoundException("Tournament with ID " + tournamentId + " does not exist."));

        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new EntityNotFoundException("Team with ID " + teamId + " does not exist."));

        if (teamStatsRepository.existsByTeamAndTournament(team, tournament)) {
            throw new DataIntegrityViolationException("Team is already part of the tournament");
        }

        TeamStats teamStats = newTeamStats(team, tournament);

        teamStatsRepository.save(teamStats);

        tournament.getTeamStats().add(teamStats);

        Tournament updatedTournament = tournamentRepository.save(tournament);
        TeamInTournamentDTO teamInTournamentDTO = new TeamInTournamentDTO(
                team.getName(),
                updatedTournament.getName(),
                updatedTournament.getStartDate(),
                updatedTournament.getEndDate());

        return teamInTournamentDTO;

    }

    public TeamStats newTeamStats(Team team, Tournament tournament) {
        TeamStats teamStats = new TeamStats();
        teamStats.setTeam(team);
        teamStats.setTournament(tournament);

        teamStats.setGoalsFor(0);
        teamStats.setGoalsAgainst(0);
        teamStats.setWins(0);
        teamStats.setLosses(0);
        teamStats.setDraws(0);

        return teamStats;

    }
}
