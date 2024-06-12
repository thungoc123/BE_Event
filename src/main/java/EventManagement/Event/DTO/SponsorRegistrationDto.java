package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class SponsorRegistrationDto {
    private String email;
    private String password;
    private String confirmPassword;
    private String companyName;
    private String companyID;
    private String fptStaffEmail;
    private String information;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}

