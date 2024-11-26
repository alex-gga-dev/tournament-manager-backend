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
@Table(name = "teams_stats")
@Data
public class TeamStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Column(name = "goals_for")
    private int goalsFor;

    @Column(name = "goals_against")
    private int goalsAgainst;

    @Column(name = "wins")
    private int wins;

    @Column(name = "losses")
    private int Losses;

    @Column(name = "draws")
    private int draws;

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
