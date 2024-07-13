package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceDTO {
    private int id;
    private String status;
    private int eventId;
    private String eventName;
    private double price;
    private String description;
    private LocalDateTime eventEndDate;
    private TicketDTO ticket;
}
