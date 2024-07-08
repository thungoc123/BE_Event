package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttendanceStatus status;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "event_id", nullable = false)
    private int eventId;

    public enum AttendanceStatus {
        ATTENDANCE,
        ABSENT
    }

    // Additional methods to get information from the associated Ticket entity
    public String getEventName() {
        return ticket.getEventName();
    }

    public double getPrice() {
        return ticket.getPrice();
    }

    public String getDescription() {
        return ticket.getDescription();
    }

    public LocalDateTime getEventEndDate() {
        return ticket.getEventEndDate();
    }
}
