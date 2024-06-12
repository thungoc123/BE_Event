package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class FeedbackDTO {
    private String title;
    private LocalDateTime deletedAt;
    private LocalDateTime modifiedAt;
    private List<FeedbackQuestionDTO> questions;
}

