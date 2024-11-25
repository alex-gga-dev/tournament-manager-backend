package agarcia.microservices.tournamentmanager.tournament_manager.entities;

import agarcia.microservices.tournamentmanager.tournament_manager.entities.enums.Position;
import agarcia.microservices.tournamentmanager.tournament_manager.validation.ValidPlayerName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ValidPlayerName
    @Column(name = "first_name")
    private String firstName;

    @ValidPlayerName
    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "red_card")
    private Integer redCard;

    @Column(name = "yellow_card")
    private Integer yellowCard;

}
