package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class CreateVisitorAnswerDTO {
    private String email;
    private int feedbackQuestionId;
    private String visitorAnswerFeedback;
}