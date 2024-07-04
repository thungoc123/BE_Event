package EventManagement.Event.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketCountRequestDTO {
    @NotNull
    private Integer eventId;
    @NotNull
    private LocalDate date;
}
