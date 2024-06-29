package EventManagement.Event.service;

import EventManagement.Event.DTO.CreateOrderDTO;
import EventManagement.Event.DTO.OrderDTO;
import EventManagement.Event.DTO.OrderDetailDTO;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Cart cart = cartRepository.findByVisitorId(visitorId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for the visitor");
        }

        // Create a new order
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderTime(LocalTime.now());
        order.setOrderState(Order.OrderState.PENDING);
        order.setCartId(cart.getCartId());

        // Calculate total
        BigDecimal total = createOrderDTO.getPrice().multiply(BigDecimal.valueOf(createOrderDTO.getQuantity()));
        order.setTotal(total);

        // Save the order
        order = orderRepository.save(order);

        // Create order details
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(order.getOrderId());
        orderDetail.setEventId(createOrderDTO.getEventId());
        orderDetail.setQuantity(createOrderDTO.getQuantity());
        orderDetail.setPrice(createOrderDTO.getPrice());

        // Save order detail
        orderDetailRepository.save(orderDetail);
        return order;
    }

    public Optional<Order> viewOrder(Integer orderId) {
        return orderRepository.findById(orderId);
    }

    public List<OrderDetail> viewOrderDetails(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderTime(order.getOrderTime());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderState(order.getOrderState().name());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setCartId(order.getCartId());
        return orderDTO;
    }

    public OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setPrice(orderDetail.getPrice());
        orderDetailDTO.setOrderId(orderDetail.getOrderId());
        orderDetailDTO.setEventId(orderDetail.getEventId());
        return orderDetailDTO;
    }

    public List<OrderDetailDTO> toOrderDetailDTOs(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(this::toOrderDetailDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::toOrderDTO)
                .collect(Collectors.toList());
    }

}
