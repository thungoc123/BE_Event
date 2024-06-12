package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private String information;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
