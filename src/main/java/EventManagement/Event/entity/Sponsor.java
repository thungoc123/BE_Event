package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
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
    private List<SponsorEvent> sponsorEvents;



    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // Getters and Setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public void setFptStaffEmail(String fptStaffEmail) {
        this.fptStaffEmail = fptStaffEmail;
    }


    public void setAccount(Account account) {
        this.account = account;
    }
}
