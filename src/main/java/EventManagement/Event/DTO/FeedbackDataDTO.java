package EventManagement.Event.DTO;

import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.FeedbackAnswer;
import EventManagement.Event.entity.FeedbackQuestion;
import lombok.Data;

import java.util.List;

@Data
public class FeedbackDataDTO {
    private List<Feedback> feedbacks;
    private List<FeedbackQuestion> feedbackQuestions;
    private List<FeedbackAnswer> feedbackAnswers;
}
