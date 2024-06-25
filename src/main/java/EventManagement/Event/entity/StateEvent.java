package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity(name = "state_event")
public class StateEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "stateEvent", cascade = CascadeType.ALL)
    private Set<Event> events;

}