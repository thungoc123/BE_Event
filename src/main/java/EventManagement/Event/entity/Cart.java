package EventManagement.Event.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int cartId;

    @OneToOne
    @JoinColumn(name = "visitor_id", referencedColumnName = "id")
    private Visitor visitor;


}
    // Getters and Setters




