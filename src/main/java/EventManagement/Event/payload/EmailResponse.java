package EventManagement.Event.payload;

import lombok.Data;

@Data
public class EmailResponse {
    private String status;
    private String message;

    public EmailResponse(String success, String message) {
    }
}
