package EventManagement.Event.controller;

import EventManagement.Event.entity.Cart;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{visitorId}/tickets")
    public ResponseEntity<List<Ticket>> viewAllTicketsOnCartByVisitorId(@PathVariable int visitorId) {
        Optional<Cart> cartOptional = cartService.findByVisitorId(visitorId);
        if (cartOptional.isPresent()) {
            List<Ticket> tickets = cartService.findTicketsByCartId(cartOptional.get().getCartId());
            return ResponseEntity.ok(tickets);
        }
        return ResponseEntity.notFound().build();
    }
}
