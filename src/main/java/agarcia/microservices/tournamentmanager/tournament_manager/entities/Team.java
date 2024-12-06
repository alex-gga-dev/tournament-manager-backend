package agarcia.microservices.tournamentmanager.tournament_manager.entities;

import agarcia.microservices.tournamentmanager.tournament_manager.validation.ValidTeamName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "teams")
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ValidTeamName
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "team_shield")
    private String teamShield;

    @Column(name = "description")
    private String description;

}
