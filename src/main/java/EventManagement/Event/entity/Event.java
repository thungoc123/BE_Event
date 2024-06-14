package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "timestart")
    private LocalDateTime timestart;
    @Column(name = "timeend")
    private LocalDateTime timeend;
    @Column(name = "timeopensale")
    private LocalDateTime timeopensale;
    @Column(name = "timeclosesale")
    private LocalDateTime timeclosesale;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
    @OneToMany(mappedBy = "event")
    @JsonManagedReference
    private List<EventImage> eventImages;
    @OneToMany(mappedBy = "event")
    @JsonManagedReference
    private List<EventSchedule> eventSchedules;
    @OneToMany(mappedBy = "event")
    private List<CheckingStaff> eventCheckingStaffs;

}
