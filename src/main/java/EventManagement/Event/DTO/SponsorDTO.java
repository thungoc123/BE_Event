package EventManagement.Event.DTO;

import jakarta.persistence.*;

public class SponsorDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String information;

    private String companyName;
    private String companyID;
    private String fptStaffEmail;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getFptStaffEmail() {
        return fptStaffEmail;
    }

    public void setFptStaffEmail(String fptStaffEmail) {
        this.fptStaffEmail = fptStaffEmail;
    }
}
