package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
    PUBLISH,
    UNPUBLISH
 }
 @ManyToOne
 @JoinColumn(name = "account_id")
 @JsonIgnore
 private Account account;
    @ManyToMany
    @JoinTable(
         name = "sponsor_program_event",
         joinColumns = @JoinColumn(name = "sponsor_program_id"),
         inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events = new HashSet<>();
}
