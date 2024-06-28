package EventManagement.Event.payload.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class InsertScheduleRequest {


    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime timeStart;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime duration;
    private String actor;
    private String description;
    private String eventType;
    private String location;


    private int eventId;

}
