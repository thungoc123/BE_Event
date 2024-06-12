package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity(name = "event_schedule")
public class EventSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "details")
    private String details;
    @Column(name = "actor")

    private String actor;
    @Column(name = "time")

    private LocalTime time;
    @Column(name = "place")
    private String place;

    @ManyToOne
    @JsonBackReference

    @JoinColumn(name = "event_details_id")
    private EventDetails eventDetails;
}