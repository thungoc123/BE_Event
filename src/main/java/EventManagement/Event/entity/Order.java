package EventManagement.Event.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Table(name = "order")
@Data
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

    @Column(name = "CartId")
    private Integer cartId;

    // Enum for OrderState
    public enum OrderState {
        CANCELLED, PENDING, PAID
    }


// <<<<<<< branchOfTuan
// =======
//     public void setQuantity(Integer quantity) {
//         this.quantity = quantity;
//     }

//     public OrderState getOrderState() {
//         return orderState;
//     }

//     public void setOrderState(OrderState orderState) {
//         this.orderState = orderState;
//     }

//     public BigDecimal getTotal() {
//         return total;
//     }

//     public void setTotal(BigDecimal total) {
//         this.total = total;
//     }

//     public Integer getCartId() {
//         return cartId;
//     }

//     public void setCartId(Integer cartId) {
//         this.cartId = cartId;
//     }
// >>>>>>> main
}
