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

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestParam Integer visitorId, @RequestBody CreateOrderDTO createOrderDTO) {
        Order order = orderService.createOrderAndAddToCart(createOrderDTO, visitorId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> viewOrder(@PathVariable Integer orderId) {
        Optional<Order> orderOptional = orderService.viewOrder(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            OrderDTO orderDTO = orderService.toOrderDTO(order);
            return ResponseEntity.ok(orderDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<List<OrderDetailDTO>> viewOrderDetails(@PathVariable Integer orderId) {
        List<OrderDetail> orderDetails = orderService.viewOrderDetails(orderId);
        if (orderDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<OrderDetailDTO> orderDetailDTOs = orderService.toOrderDetailDTOs(orderDetails);
        return ResponseEntity.ok(orderDetailDTOs);
    }
}