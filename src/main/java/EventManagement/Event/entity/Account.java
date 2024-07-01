package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Entity(name = "account")
@Data
@EqualsAndHashCode(of = "id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;



    @OneToMany(mappedBy = "account")
    @JsonIgnore
    @JsonIgnoreProperties("account") // Ignore the account field in Visitor to prevent recursion
    private Set<Visitor> visitors;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    @JsonIgnoreProperties("account") // Ignore the account field in EventOperator to prevent recursion
    private Set<EventOperator> eventOperators;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    @JsonIgnoreProperties("account") // Ignore the account field in Sponsor to prevent recursion
    private Set<Sponsor> sponsors;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties("account") // Ignore the account field in CheckingStaff to prevent recursion
    private Set<CheckingStaff> checkingStaffs;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    @JsonIgnore
    @JsonIgnoreProperties("account") // Ignore the account field in SponsorProgram to prevent recursion
    private List<SponsorProgram> sponsorPrograms;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    @JsonIgnore
    @JsonIgnoreProperties("account") // Ignore the account field in Event to prevent recursion
    private List<Event> events;
}
