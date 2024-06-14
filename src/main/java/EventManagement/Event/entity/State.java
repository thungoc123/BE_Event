package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
}
