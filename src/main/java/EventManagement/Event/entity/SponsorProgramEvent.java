package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "sponsor_program_event")
@IdClass(SponsorProgramEventId.class)
public class SponsorProgramEvent {
    @Id
    @Column(name = "event_id")
    private int eventId;

    @Id
    @Column(name = "sponsor_program_id")
    private int sponsorProgramId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("sponsorProgramId")
    @JoinColumn(name = "sponsor_program_id", referencedColumnName = "id")
    @JsonBackReference
    private SponsorProgram sponsorProgram;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("eventId")
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @JsonBackReference
    private Event event;


}
