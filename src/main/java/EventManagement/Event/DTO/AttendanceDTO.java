package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AttendanceDTO {
    private int id;
    private String status;
    private TicketDTO ticket;
    private int eventId;
    private String eventName;
    private double price;
    private LocalDateTime eventEndDate;
    private String description;
}
