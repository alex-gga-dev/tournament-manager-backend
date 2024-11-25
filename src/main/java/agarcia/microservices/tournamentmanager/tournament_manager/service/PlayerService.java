package agarcia.microservices.tournamentmanager.tournament_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import agarcia.microservices.tournamentmanager.tournament_manager.dtos.PlayerDTO;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Player;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.Team;
import agarcia.microservices.tournamentmanager.tournament_manager.exceptions.NoDataFoundException;
import agarcia.microservices.tournamentmanager.tournament_manager.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    public PlayerService(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Player getByName(String name) {
        return playerRepository.findByFirstName(name)
                .orElseThrow(() -> new NoDataFoundException(
                        "Player with name " + name + " not found"));

    }

    public PlayerDTO save(PlayerDTO playerDto) {

        validateDuplicatePlayer(playerDto);

        Team team = teamService.findById(playerDto.idTeam()).orElseThrow(
                () -> new EntityNotFoundException("Team with ID " + playerDto.idTeam() + " does not exist."));

        Player player = mapToPlayerEntity(playerDto, team);

        playerRepository.save(player);

        return playerDto;

    }

    public PlayerDTO update(Long id, Player player) {
        // Buscar el jugador a actualizar
        Player playerToUpdate = playerRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException(
                        "Player with ID " + id + " not found"));

        // Actualizar solo los campos enviados (no nulos)
        if (player.getFirstName() != null) {
            playerToUpdate.setFirstName(player.getFirstName());
        }
        if (player.getLastName() != null) {
            playerToUpdate.setLastName(player.getLastName());
        }
        if (player.getPosition() != null) {
            playerToUpdate.setPosition(player.getPosition());
        }
        if (player.getRedCard() != null) {
            playerToUpdate.setRedCard(player.getRedCard());
        }
        if (player.getYellowCard() != null) {
            playerToUpdate.setYellowCard(player.getYellowCard());
        }
        if (player.getTeam() != null) {
            if (player.getTeam().getId() != null) {
                Team team = teamService.findById(player.getTeam().getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Team with ID " + player.getTeam().getId() + " not found."));
                playerToUpdate.setTeam(team);
            }
        }

        Player updatedPlayer = playerRepository.save(playerToUpdate);

        return new PlayerDTO(
                updatedPlayer.getFirstName(),
                updatedPlayer.getLastName(),
                updatedPlayer.getPosition(),
                updatedPlayer.getTeam() != null ? updatedPlayer.getTeam().getId() : null);

    }

    public void delete(Long id) {
        Player playerToDelete = playerRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException(
                        "Player with ID " + id + " not found"));

        playerRepository.delete(playerToDelete);

    }

    public List<Player> getPlayers() {
        List<Player> players = playerRepository.findAll();
        return players;
    }

    public void validateDuplicatePlayer(PlayerDTO playerDTO) {
        Optional<Player> existingPlayer = playerRepository.findByFullNameAndTeam(
                playerDTO.firstName(),
                playerDTO.lastName(),
                playerDTO.idTeam());

        if (existingPlayer.isPresent()) {
            throw new DataIntegrityViolationException("Player already exists with this name in the same team.");
        }
    }

    public Player mapToPlayerEntity(PlayerDTO playerDTO, Team team) {
        Player player = new Player();

        player.setFirstName(playerDTO.firstName());
        player.setLastName(playerDTO.lastName());
        player.setPosition(playerDTO.position());
        player.setRedCard(0);
        player.setYellowCard(0);
        player.setTeam(team);

        return player;
    }
}
