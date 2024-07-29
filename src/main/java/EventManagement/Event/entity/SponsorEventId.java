package EventManagement.Event.entity;

import lombok.Data;

@Data
public class SponsorEventId {
    private static final long serialVersionUID = 1L;

    private int eventId;
    private Long sponsorId;


}
