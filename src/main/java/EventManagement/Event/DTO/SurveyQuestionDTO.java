package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SurveyQuestionDTO {
    private String typeQuestion;
    private LocalDateTime modifiedAt;
    private LocalDateTime deleteAt;

}
