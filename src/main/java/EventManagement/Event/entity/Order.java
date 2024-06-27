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

    // Getters and Setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }
}
