package EventManagement.Event.controller;

import EventManagement.Event.DTO.TicketRequestDTO;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
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
    public ResponseEntity<?> getAllTickets() {
        try {
            List<Ticket> tickets = ticketService.findAll();
            if (tickets.isEmpty()) {
                return ResponseEntity.ok(Collections.singletonList("Have nothing to view on list"));
            } else {
                return ResponseEntity.ok(tickets);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        try {
            Optional<Ticket> ticket = ticketService.findById(id);
            if (ticket.isPresent()) {
                return ResponseEntity.ok(ticket.get());
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Ticket you find is not exist");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTicket(@PathVariable int id) {
        try {
            boolean isDeleted = ticketService.deleteTicket(id);
            if (isDeleted) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Successfully deleted the ticket!"));
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Can't delete, the ticket does not exist!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateTicketStatus(@PathVariable int id, @RequestBody TicketRequestDTO ticketDetails) {
        try {
            Optional<Ticket> updatedTicket = ticketService.updateTicketStatus(id, ticketDetails.getStatus());
            if (updatedTicket.isPresent()) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Ticket updated successfully"));
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Can't update, the ticket does not exist!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @PostMapping("/create_ticket_order")
    public ResponseEntity<Map<String, String>> createOrderTicket(@Valid @RequestBody TicketRequestDTO ticketRequest) {
        try {
            Optional<Map<String, String>> result = ticketService.createOrderTicket(ticketRequest.getVisitorId(), ticketRequest.getEventId(), ticketRequest.isStatusCart(), ticketRequest.getStatus());
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Can't create, the visitor or event does not exist!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/count/paid/{eventId}")
    public ResponseEntity<Map<String, Object>> countPaidTicketsByEventId(@PathVariable int eventId) {
        try {
            Optional<Map<String, Object>> result = ticketService.countPaidTicketsByEventId(eventId);
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Event not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/visitors/paid/{eventId}")
    public ResponseEntity<?> findVisitorsByEventIdAndStatusPaid(@PathVariable int eventId) {
        try {
            List<Object> visitors = ticketService.findVisitorsByEventIdAndStatusPaid(eventId);
            if (visitors.size() == 1 && "Data is null".equals(visitors.get(0))) {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "No visitors found with status PAID for the given event"));
            }
            return ResponseEntity.ok(visitors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/event/{eventId}/tickets")
    public ResponseEntity<?> viewTicketsByEventAndDate(@PathVariable int eventId,
                                                       @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDateTime startDate,
                                                       @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDateTime endDate) {
        return ticketService.viewTicketsByEventAndDate(eventId, startDate, endDate)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(Collections.singletonMap("message", "Event not found or no tickets found within the date range")));
    }

    @GetMapping("/count-tickets")
    public ResponseEntity<?> countTicketsByEventIdAndDate(
            @RequestParam("eventId") int eventId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDateTime dateTime) {
        return ticketService.countTicketsByEventIdAndDate(eventId, dateTime)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(Collections.singletonMap("message", "Event not found or date out of range")));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getVisitorEmailAndTicketPrice(@RequestParam int ticketId) {
        try {
            Map<String, Object> response = ticketService.getVisitorEmailAndTicketPrice(ticketId);
            if (response.containsKey("message") && response.get("message").equals("Ticket not found")) {
                return ResponseEntity.status(404).body(response);
            } else {
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/status/paid")
    public ResponseEntity<List<Ticket>> getAllTicketsWithStatusPaid() {
        List<Ticket> tickets = ticketService.getTicketsByStatusAndCart(Ticket.Status.PAID);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/status/pending")
    public ResponseEntity<List<Ticket>> getAllTicketsWithStatusPending() {
        List<Ticket> tickets = ticketService.getTicketsByStatusAndCart(Ticket.Status.PENDING);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/status/cancelled")
    public ResponseEntity<List<Ticket>> getAllTicketsWithStatusCancelled() {
        List<Ticket> tickets = ticketService.getTicketsByStatusAndCart(Ticket.Status.CANCELLED);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Ticket>> getTicketsInCart() {
        List<Ticket> tickets = ticketService.getTicketsInCart();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/visitorId")
    public ResponseEntity<?> getVisitorIdsByAccountId(@RequestParam int accountId) {
        try {
            Optional<List<Map<String, Object>>> response = ticketService.findVisitorIdsByAccountId(accountId);
            if (response.isPresent()) {
                return ResponseEntity.ok(response.get());
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Account not found or visitor not associated with the account"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/total-amount/{eventId}")
    public ResponseEntity<?> getTotalAmountRaised(@PathVariable int eventId) {
        try {
            Optional<Map<String, Object>> result = ticketService.calculateTotalAmountRaised(eventId);
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Event not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/total-participants/{eventId}")
    public ResponseEntity<?> getTotalParticipants(@PathVariable int eventId) {
        try {
            Optional<Map<String, Object>> result = ticketService.countTotalParticipants(eventId);
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Event not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }
}
