package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class FeedbackVisitorDTO {
    private int questionId;
    private String questionText;
    private int answerId;
    private String answerText;
}
