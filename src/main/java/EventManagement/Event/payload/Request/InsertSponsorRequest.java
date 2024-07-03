package EventManagement.Event.payload.Request;

import lombok.Data;

@Data
public class InsertSponsorRequest {
      private Long sponsorId;
      private int eventId;
      private double profitPercentage;
}
