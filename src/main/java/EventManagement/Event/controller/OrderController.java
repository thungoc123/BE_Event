package EventManagement.Event.controller;

import EventManagement.Event.DTO.CreateOrderDTO;
import EventManagement.Event.DTO.OrderDTO;
import EventManagement.Event.DTO.OrderDetailDTO;
import EventManagement.Event.entity.Order;
import EventManagement.Event.entity.OrderDetail;
import EventManagement.Event.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api-orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestParam Integer visitorId, @RequestBody CreateOrderDTO createOrderDTO) {
        Order order = orderService.createOrderAndAddToCart(createOrderDTO, visitorId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public OrderDTO viewOrder(@PathVariable Integer orderId) {
        Optional<Order> orderOptional = orderService.viewOrder(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setOrderTime(order.getOrderTime());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setQuantity(order.getQuantity());
            orderDTO.setOrderState(order.getOrderState().name());
            orderDTO.setTotal(order.getTotal());
            orderDTO.setCartId(order.getCartId());
            return orderDTO;
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    @GetMapping("/{orderId}/details")
    public List<OrderDetailDTO> viewOrderDetails(@PathVariable Integer orderId) {
        List<OrderDetail> orderDetails = orderService.viewOrderDetails(orderId);
        return orderDetails.stream().map(orderDetail -> {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setOrderDetailId(orderDetail.getOrderDetailId());
            orderDetailDTO.setQuantity(orderDetail.getQuantity());
            orderDetailDTO.setPrice(orderDetail.getPrice());
            orderDetailDTO.setOrderId(orderDetail.getOrderId());
            orderDetailDTO.setEventId(orderDetail.getEventId());
            return orderDetailDTO;
        }).collect(Collectors.toList());
    }
}