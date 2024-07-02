package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class TicketRequestDTO {
    private int cartId;
    private int eventId;
    private int quantity;
}