package EventManagement.Event.payload.Request;


import lombok.Data;

@Data
public class InsertCheckingStaffRequest {
     private String email;
     private int accountId;
     private String information;
     private int eventId;

}
