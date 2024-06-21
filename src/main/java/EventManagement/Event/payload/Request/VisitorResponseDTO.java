package EventManagement.Event.payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitorResponseDTO {
    private String message;
    private boolean success;
}

