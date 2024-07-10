package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

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

    @JsonIgnore
    @OneToOne(mappedBy = "visitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VisitorAnswer> visitorAnswers;
}