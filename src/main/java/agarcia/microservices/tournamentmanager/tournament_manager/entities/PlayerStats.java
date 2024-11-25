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
@Table(name = "players_stats")
@Data
public class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "total_goals")
    private int totalGoals;

    @Column(name = "total_red_cards")
    private int totalRedCards;

    @Column(name = "total_yellow_cards")
    private int totalYellowCards;

}
