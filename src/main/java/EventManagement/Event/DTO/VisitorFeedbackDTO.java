package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class VisitorFeedbackDTO {
    private int visitorId;
    private int feedbackId;
    private String email;
}
