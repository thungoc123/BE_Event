package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDTO {
    private int id;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
    private String status;
    private String eventName;
    private double price;
    private String description;
    private LocalDateTime eventEndDate;
    private VisitorDTO visitor;
}
