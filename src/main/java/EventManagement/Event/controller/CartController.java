package EventManagement.Event.controller;

import EventManagement.Event.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;

@RestController
@RequestMapping("/api-cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{visitorId}/tickets")
    public ResponseEntity<?> viewAllTicketsOnCartByVisitorId(@PathVariable int visitorId) {
        Optional<Cart> cartOptional = cartService.findByVisitorId(visitorId);
        if (cartOptional.isPresent()) {
            List<Ticket> tickets = cartService.findTicketsByCartId(cartOptional.get().getCartId());
            return ResponseEntity.ok(tickets);
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Cart not found for the visitor"));
    }

    @GetMapping("/{visitorId}/tickets/{ticketId}")
    public ResponseEntity<?> viewTicketByVisitorIdAndTicketId(@PathVariable int visitorId, @PathVariable int ticketId) {
        Optional<Cart> cartOptional = cartService.findByVisitorId(visitorId);
        if (cartOptional.isPresent()) {
            Optional<Ticket> ticketOptional = cartService.findTicketByCartIdAndTicketId(cartOptional.get().getCartId(), ticketId);
            return ticketOptional.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).body((Ticket) Collections.singletonMap("message", "Ticket not found in the cart")));
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Cart not found for the visitor"));
    }

    @GetMapping("/{visitorId}/tickets/count")
    public ResponseEntity<?> countTicketsByVisitorId(@PathVariable int visitorId) {
        Optional<Cart> cartOptional = cartService.findByVisitorId(visitorId);
        if (cartOptional.isPresent()) {
            long count = cartService.countTicketsByCartId(cartOptional.get().getCartId());
            return ResponseEntity.ok(Collections.singletonMap("Amount ticket", count));
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Cart not found for the visitor"));
    }

    @GetMapping("/{visitorId}/tickets/by-status")
    public ResponseEntity<?> viewTicketsByStatusByVisitorId(@PathVariable int visitorId) {
        Optional<Cart> cartOptional = cartService.findByVisitorId(visitorId);
        if (cartOptional.isPresent()) {
            Map<Ticket.Status, List<Ticket>> ticketsByStatus = cartService.findTicketsByCartIdGroupedByStatus(cartOptional.get().getCartId());
            return ResponseEntity.ok(ticketsByStatus);
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Cart not found for the visitor"));
    }
}
