package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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
    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

}
