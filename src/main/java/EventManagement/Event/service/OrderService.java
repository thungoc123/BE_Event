package EventManagement.Event.service;

import EventManagement.Event.DTO.CreateOrderDTO;
import EventManagement.Event.entity.Cart;
import EventManagement.Event.entity.Order;
import EventManagement.Event.entity.OrderDetail;
import EventManagement.Event.repository.CartRepository;
import EventManagement.Event.repository.OrderDetailRepository;
import EventManagement.Event.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Order createOrderAndAddToCart(CreateOrderDTO createOrderDTO, Integer visitorId) {
        // Create Order
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderTime(LocalTime.now());
        order.setQuantity(createOrderDTO.getQuantity());
        order.setOrderState(Order.OrderState.PENDING);
        order.setTotal(createOrderDTO.getPrice().multiply(new BigDecimal(createOrderDTO.getQuantity())));
        order.setVisitorId(visitorId);
        Order savedOrder = orderRepository.save(order);

        // Create OrderDetail
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(savedOrder.getOrderId());
        orderDetail.setEventId(createOrderDTO.getEventId());
        orderDetail.setQuantity(createOrderDTO.getQuantity());
        orderDetail.setPrice(createOrderDTO.getPrice());
        orderDetail.setStatus(OrderDetail.Status.OPEN);
        orderDetailRepository.save(orderDetail);

        // Create Cart
        Cart cart = new Cart();
        cart.setOrderId(savedOrder.getOrderId());
        cart.setVisitorId(visitorId);
        cart.setCreatedAt(LocalDate.now().atStartOfDay());
        cart.setModifiedAt(LocalDate.now().atStartOfDay());
        cartRepository.save(cart);

        return savedOrder;
    }
}
