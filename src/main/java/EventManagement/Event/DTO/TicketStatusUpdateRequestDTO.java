package EventManagement.Event.DTO;

import EventManagement.Event.entity.Ticket;

public class TicketStatusUpdateRequestDTO {
    private Ticket.Status status;

    // Getters and Setters
    public Ticket.Status getStatus() {
        return status;
    }

    public void setStatus(Ticket.Status status) {
        this.status = status;
    }
}
