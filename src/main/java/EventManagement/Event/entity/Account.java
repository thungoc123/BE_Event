//package EventManagement.Event.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import java.util.List;
//import java.util.Set;
//
//@Entity(name = "account")
//@Data
//@EqualsAndHashCode(of = "id")
//public class Account {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne
////    @JsonManagedReference
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//    @Column(nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false)
//    private boolean enabled = true; // Thêm trường này
//
//    @OneToMany(mappedBy = "account")
//    @JsonIgnore
//    @JsonIgnoreProperties("account")
//    private Set<Visitor> visitors;
//
//    @OneToMany(mappedBy = "account")
//    @JsonIgnore
//    @JsonIgnoreProperties("account")
//    private Set<EventOperator> eventOperators;
//
//    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
//    @JsonManagedReference
//    @JsonIgnoreProperties("account")
//    private Set<Sponsor> sponsors;
//
//    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
//    @JsonBackReference
////    @JsonIgnoreProperties("account")
//    private Set<CheckingStaff> checkingStaffs;
//
//    @OneToMany(mappedBy = "account")
//    @JsonManagedReference
//    @JsonIgnore
//    @JsonIgnoreProperties("account")
//    private List<SponsorProgram> sponsorPrograms;
//
//    @OneToMany(mappedBy = "account")
//    @JsonManagedReference
//    @JsonIgnore
//    @JsonIgnoreProperties("account")
//    private List<Event> events;
//}

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

    @Column(nullable = false)
    private boolean enabled = true; // Thêm trường này

    @Column(name = "reset_token")
    private String resetToken;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    @JsonIgnoreProperties("account")
    private Set<Visitor> visitors;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    @JsonIgnoreProperties("account")
    private Set<EventOperator> eventOperators;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    @JsonIgnoreProperties("account")
    private Set<Sponsor> sponsors;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties("account")
    private Set<CheckingStaff> checkingStaffs;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    @JsonIgnore
    @JsonIgnoreProperties("account")
    private List<SponsorProgram> sponsorPrograms;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    @JsonIgnore
    @JsonIgnoreProperties("account")
    private List<Event> events;
}
