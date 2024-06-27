package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity(name = "event_schedule")
public class EventSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "actor")
    private String actor;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "timestart")
    private LocalTime timestart;
    @Column(name = "duration")
    private LocalTime duration;
    @Column(name = "event_type")
    private String eventType;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "location")
    private String location;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "event_id")
    private Event event;
}