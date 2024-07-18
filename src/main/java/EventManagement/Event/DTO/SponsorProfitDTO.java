package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class SponsorProfitDTO {
    private Long sponsorId;
    private String companyName;
    private String sponsorEmail;
    private Double sponsorProfitPercent;
    private Double profitAmount;
}