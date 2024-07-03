package EventManagement.Event.DTO;

import EventManagement.Event.entity.Ticket.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequestDTO {
    private Integer cartId;
    private Integer eventId;
    private Integer quantity;
    private Status status;
}
