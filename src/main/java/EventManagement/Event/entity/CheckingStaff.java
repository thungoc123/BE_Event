package EventManagement.Event.entity;

import jakarta.persistence.*;

@Entity
public class CheckingStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String information;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
