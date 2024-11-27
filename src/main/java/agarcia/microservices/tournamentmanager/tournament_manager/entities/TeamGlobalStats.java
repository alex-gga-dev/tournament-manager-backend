package agarcia.microservices.tournamentmanager.tournament_manager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "teams_global_stats")
@Data
public class TeamGlobalStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "global_goals_for")
    private int globalGoalsFor;

    @Column(name = "global_goals_against")
    private int globalGoalsAgainst;

    @Column(name = "global_wins")
    private int globalWins;

    @Column(name = "global_losses")
    private int globalLosses;

    @Column(name = "global_draws")
    private int globalDraws;
}
