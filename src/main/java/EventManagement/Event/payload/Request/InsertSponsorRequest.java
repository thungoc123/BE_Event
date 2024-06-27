package EventManagement.Event.payload.Request;

import lombok.Data;

@Data
public class InsertSponsorRequest {
      private String email;
      private int eventId;
}
