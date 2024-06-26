package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity(name = "account")
@Data
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

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "account")
    private Set<Visitor> visitors;

    @OneToMany(mappedBy = "account")
    private Set<EventOperator> eventOperators;

    @OneToMany(mappedBy = "account")
    private Set<Sponsor> sponsors;

    @OneToMany(mappedBy = "account")
    private Set<CheckingStaff> checkingStaffs;


    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<Event> events;


    // Constructors, getters, setters, and other fields as needed
}
