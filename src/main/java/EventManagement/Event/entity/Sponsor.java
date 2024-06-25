package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity(name = "sponsor")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "information")
    private String information;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "companyid")
    private String companyID;
    @Column(name = "fpt_staff_email")
    private String fptStaffEmail;

    @OneToMany(mappedBy = "sponsor")
    private List<Event> events;


    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
