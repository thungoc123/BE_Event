package EventManagement.Event.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Integer orderId;

    @Column(name = "OrderTime")
    private LocalTime orderTime;

    @Column(name = "OrderDate")
    private LocalDate orderDate;

    @Column(name = "Quantity")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "OrderState")
    private OrderState orderState;

    @Column(name = "Total")
    private BigDecimal total;

    @Column(name = "VisitorId")
    private Integer visitorId;

    // Enum for OrderState
    public enum OrderState {
        CANCELLED, PENDING, PAID
    }


}
