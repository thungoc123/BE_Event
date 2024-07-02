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
    @OneToOne(mappedBy = "visitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;


}
