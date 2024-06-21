package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "sponsor_program")
   public class SponsorProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "link")
    private String link;
    @Column(name = "location")
    private String location;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
    PUBLISH,
    UNPUBLISH
 }
    @ManyToMany
    @JoinTable(
         name = "sponsor_program_event",
         joinColumns = @JoinColumn(name = "sponsor_program_id"),
         inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events = new HashSet<>();
}
