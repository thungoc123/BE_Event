package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitorAnswerDTO {
    private int id;
    private int visitorId;
    private int feedbackQuestionId;
    private String visitorAnswerFeedback;

    private VisitorDTO visitorDTO;

    private FeedbackQuestionDTO feedbackQuestionDTO;

    private int questionId;
    private String questionText;
    private int answerId;
    private String answerText;



}
