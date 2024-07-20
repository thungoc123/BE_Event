package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "event_profit")
public class EventProfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "event_id", unique = true, nullable = false)
    private Integer eventId;

    @Column(name = "total_profit")
    private Double totalProfit;

    // Getters and Setters
}
