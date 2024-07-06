package EventManagement.Event.controller;

import EventManagement.Event.DTO.TicketCountRequestDTO;
import EventManagement.Event.DTO.TicketRequestDTO;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.service.TicketService;
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
            Map<String, String> response = ticketService.deleteTicket(id);
            if (response.containsKey("message") && response.get("message").equals("Successfully deleted the ticket!")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Can't delete, the ticket does not exist!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateTicketStatusAndQuantity(@PathVariable int id, @RequestBody TicketRequestDTO ticketDetails) {
        try {
            Optional<Ticket> updatedTicket = ticketService.updateTicketStatusAndQuantity(id, ticketDetails.getQuantity(), ticketDetails.getStatus());
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
    public ResponseEntity<Map<String, String>> createOrderTicket(@RequestBody TicketRequestDTO ticketRequest) {
        try {
            Optional<Map<String, String>> result = ticketService.createOrderTicket(ticketRequest.getCartId(), ticketRequest.getEventId(), ticketRequest.getQuantity());
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Can't create, the cart or event does not exist!"));
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
            if (visitors.isEmpty() || (visitors.size() == 1 && "Data is null".equals(visitors.get(0)))) {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "No visitors found with status PAID for the given event"));
            }
            return ResponseEntity.ok(visitors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/event/{eventId}/tickets")
    public ResponseEntity<?> viewTicketsByEventAndDate(@PathVariable int eventId,
                                                       @RequestParam("startDate") String startDateStr,
                                                       @RequestParam("endDate") String endDateStr) {
        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr);
            Optional<Map<String, Object>> result = ticketService.viewTicketsByEventAndDate(eventId, startDate, endDate);
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Event not found or no tickets found within the date range"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/count-tickets")
    public ResponseEntity<?> countTicketsByEventIdAndDate(
            @RequestParam("eventId") int eventId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            Optional<Map<String, Object>> result = ticketService.countTicketsByEventIdAndDate(eventId, date);
            if (result.isPresent()) {
                return ResponseEntity.ok(result.get());
            } else {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Event not found or date out of range"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "An unknown error occurred: " + e.getMessage()));
        }
    }
}
