package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference(value = "cart-ticket")
    private Cart cart;

    @Column(name = "quantity")
    private int quantity;

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

    // Derived fields from the Event entity
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
    private void validateExpiredDate() {
        if (expiredDate.isAfter(event.getTimeend())) {
            throw new IllegalArgumentException("Expired date cannot be after the event end date");
        }
    }
}
