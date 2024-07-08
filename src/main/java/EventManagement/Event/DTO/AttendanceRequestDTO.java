package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class AttendanceRequestDTO {
    private int ticketId;
    private String status; // This will be hardcoded to "ABSENT" in the code
}
