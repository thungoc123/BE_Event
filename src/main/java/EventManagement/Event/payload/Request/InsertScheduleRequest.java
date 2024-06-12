package EventManagement.Event.payload.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;
@Data
public class InsertScheduleRequest {
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    private String actor;

    private String place;
    private String details;
}
