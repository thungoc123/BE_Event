package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackAnswerDTO {
    private int feedbackAnswerID;
    private String answer;
    private LocalDateTime deletedAt;
    private LocalDateTime modifiedAt;
    private int question_id;
}
