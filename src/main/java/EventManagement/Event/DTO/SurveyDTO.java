package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SurveyDTO {
    private String title;
    private String target;
    private LocalDateTime modifiedAt;
    private LocalDateTime deleteAt;
}
