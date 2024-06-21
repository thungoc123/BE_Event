package EventManagement.Event.payload.Request;

import EventManagement.Event.entity.Event;
import lombok.Data;

import java.util.List;

@Data
public class InsertSponsorProgramRequest {
    private String title;
    private String websiteLink;
    private String location;
    private String thumbnail;
    private String description;
    private String state;
    private List<Integer> eventIds;
}
