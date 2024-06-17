package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "checking_staff")
public class CheckingStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
