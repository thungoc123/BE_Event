package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackAnswerDTO {
    private String answer;
    private LocalDateTime deletedAt;
    private LocalDateTime modifiedAt;
}
