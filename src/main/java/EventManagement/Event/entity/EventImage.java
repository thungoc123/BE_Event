package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "event_image")
public class EventImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String url;

    @ManyToOne
    @JsonBackReference

    @JoinColumn(name = "event_id")
    private Event event;
}
