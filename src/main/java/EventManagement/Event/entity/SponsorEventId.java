package EventManagement.Event.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class SponsorEventId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long sponsorId;
    private int eventId;

}

