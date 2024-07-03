package EventManagement.Event.controller;

import EventManagement.Event.DTO.TicketRequestDTO;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.entity.Visitor;
import EventManagement.Event.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api-ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
        Optional<Ticket> ticket = ticketService.findById(id);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTicket(@PathVariable int id) {
        Map<String, String> response = ticketService.deleteTicket(id);
        if (response.containsKey("message") && response.get("message").equals("Successfully deleted the ticket!")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ticket> updateTicketStatusAndQuantity(@PathVariable int id, @RequestBody Ticket ticketDetails) {
        Optional<Ticket> updatedTicket = ticketService.updateTicketStatusAndQuantity(id, ticketDetails.getQuantity(), ticketDetails.getStatus());
        return updatedTicket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create_ticket_order")
    public ResponseEntity<Ticket> createOrderTicket(@RequestBody TicketRequestDTO ticketRequest) {
        Optional<Ticket> ticketOptional = ticketService.createOrderTicket(ticketRequest.getCartId(), ticketRequest.getEventId(), ticketRequest.getQuantity());
        return ticketOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/count/paid/{eventId}")
    public ResponseEntity<Map<String, Object>> countPaidTicketsByEventId(@PathVariable int eventId) {
        Optional<Map<String, Object>> result = ticketService.countPaidTicketsByEventId(eventId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/visitors/paid/{eventId}")
    public ResponseEntity<List<Object>> findVisitorsByEventIdAndStatusPaid(@PathVariable int eventId) {
        List<Object> visitors = ticketService.findVisitorsByEventIdAndStatusPaid(eventId);
        return ResponseEntity.ok(visitors);
    }
}
