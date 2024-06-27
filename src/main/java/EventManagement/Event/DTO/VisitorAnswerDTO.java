package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class VisitorAnswerDTO {
    private int visitorId;
    private int feedbackAnswerId;
    private String visitorAnswerFeedback;
    private int visitorCount;
    private int id;
}
