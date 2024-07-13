package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference(value = "event-ticket")
    private Event event;

    @OneToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    @JsonBackReference(value = "visitor-ticket")
    private Visitor visitor;

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        PENDING,
        PAID,
        CANCELLED
    }

    //Getter and Setter to get something on event table
    public String getEventName() {
        return event.getName();
    }

    public double getPrice() {
        return event.getPrice();
    }

    public String getDescription() {
        return event.getDescription();
    }

    public LocalDateTime getEventEndDate() {
        return event.getTimeend();
    }

    @PrePersist
    @PreUpdate
    private void validate() {
        if (expiredDate != null && expiredDate.isAfter(event.getTimeend())) {
            throw new IllegalArgumentException("Expired date cannot be after the event end date");
        }
        if (visitor == null || visitor.getId() <= 0) {
            throw new IllegalArgumentException("Visitor must be set and valid");
        }
    }
}
