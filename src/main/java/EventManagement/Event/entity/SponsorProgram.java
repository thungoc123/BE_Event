package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "sponsor_program")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @OneToMany(mappedBy = "sponsorProgram", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SponsorProgramEvent> sponsorProgramEvents;
}
