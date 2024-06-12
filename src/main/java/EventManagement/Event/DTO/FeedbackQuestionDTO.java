package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedbackQuestionDTO {
    private String typeQuestion;
    private String textQuestion;
    private LocalDateTime deletedAt;
    private LocalDateTime modifiedAt;
    private List<FeedbackAnswerDTO> answers;
}
