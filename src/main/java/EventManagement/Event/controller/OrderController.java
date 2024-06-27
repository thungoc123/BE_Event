package EventManagement.Event.controller;

import EventManagement.Event.DTO.CreateOrderDTO;
import EventManagement.Event.entity.Order;
import EventManagement.Event.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order createOrderAndAddToCart(@RequestBody CreateOrderDTO createOrderDTO, @RequestParam Integer visitorId) {
        return orderService.createOrderAndAddToCart(createOrderDTO, visitorId);
    }
}