package EventManagement.Event.DTO;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.FeedbackQuestion;
import lombok.Data;

import java.util.List;
@Data
public class FeedbackQuestionEventDTO {
    private List<FeedbackQuestion> feedbackQuestions;
    private List<Event> events;

}

