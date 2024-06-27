package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "state")
@Data
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private int stateId;

    @Column(name = "state_name")
    private String stateName;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;


}
