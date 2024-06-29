package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class FeedbackDTO {
    private int feedbackID;
    private String title;
    private LocalDateTime deletedAt;
    private LocalDateTime modifiedAt;
    private int stateID;
   private int eventid;


    private List<FeedbackQuestionDTO> feedbackQuestions;


}

