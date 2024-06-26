package EventManagement.Event.payload;

import lombok.Data;

@Data
public class BaseResponse {
    private int statusCode;
    private String message;
    private Object data;
    private String role_name;
    private boolean success;
}
