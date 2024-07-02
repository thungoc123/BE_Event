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
    public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
        Optional<Ticket> ticketOptional = ticketService.findById(id);

        if (ticketOptional.isPresent()) {
            ticketService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<Map<String, Long>> countPaidTicketsByEventId(@PathVariable int eventId) {
        long count = ticketService.countPaidTicketsByEventId(eventId);
        Map<String, Long> response = new HashMap<>();
        response.put("At that event the amount of visitor by that ticket is:  ", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/visitors/paid/{eventId}")
    public ResponseEntity<List<Visitor>> findVisitorsByEventIdAndStatusPaid(@PathVariable int eventId) {
        List<Visitor> visitors = ticketService.findVisitorsByEventIdAndStatusPaid(eventId);
        return ResponseEntity.ok(visitors);
    }
}
