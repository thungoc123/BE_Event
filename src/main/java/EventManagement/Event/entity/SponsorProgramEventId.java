package EventManagement.Event.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
@Data
public class SponsorProgramEventId{
    private static final long serialVersionUID = 1L;
    private int sponsorProgramId;
    private int eventId;
}
