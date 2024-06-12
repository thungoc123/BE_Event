package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity(name = "event_details")
public class EventDetails {


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;



    @Column(name ="client")
    private String client;
    @Column(name ="location")
    private String location;
    @Column(name ="duration")
    private LocalTime duration;
    @Column(name ="time")
    private LocalTime time;
    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonBackReference

    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "eventDetails" )
    @JsonManagedReference
    private List<EventSchedule> eventSchedules;


}
