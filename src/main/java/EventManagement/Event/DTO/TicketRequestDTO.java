package EventManagement.Event.DTO;

import EventManagement.Event.entity.Ticket;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequestDTO {
    @NotNull
    private Integer visitorId;

    @NotNull
    private Integer eventId;

    @NotNull
    private Ticket.Status status;

    private boolean statusCart;
}
