package EventManagement.Event.payload.Request;

import lombok.Data;

import java.util.List;

@Data
public class AddEventsToSponsorProgramRequest {
    private int sponsorProgramId;
    private List<Integer> eventIds;
}
