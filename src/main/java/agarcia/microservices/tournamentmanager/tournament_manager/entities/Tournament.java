package agarcia.microservices.tournamentmanager.tournament_manager.entities;

import java.time.LocalDate;
import java.util.List;
import agarcia.microservices.tournamentmanager.tournament_manager.entities.enums.TournamentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tournament")
@Data
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private TournamentStatus status;

    @Column(name = "points_for_win")
    private int pointsForWin;

    @Column(name = "points_for_draw")
    private int pointsForDraw;

    @Column(name = "points_for_loss")
    private int pointsForLoss;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<TeamStats> teamStats;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<Game> games;
}
