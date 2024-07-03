package EventManagement.Event.service;

import EventManagement.Event.entity.Cart;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.entity.Visitor;
import EventManagement.Event.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private EventService eventService;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteById(int id) {
        ticketRepository.deleteById(id);
    }

    public Map<String, String> deleteTicket(int id) {
        Map<String, String> response = new HashMap<>();
        Optional<Ticket> ticketOptional = findById(id);

        if (ticketOptional.isPresent()) {
            deleteById(id);
            response.put("message", "Successfully deleted the ticket!");
        } else {
            response.put("message", "The ticket maybe not exist?");
        }

        return response;
    }

    public Optional<Ticket> updateTicketStatusAndQuantity(int id, int quantity, Ticket.Status status) {
        try {
            Optional<Ticket> ticketOptional = findById(id);
            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                ticket.setQuantity(quantity);
                ticket.setStatus(status);
                return Optional.of(ticketRepository.save(ticket));
            } else {
                System.err.println("Ticket not found for given ID: " + id);
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Error occurred while updating the ticket: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Ticket> createOrderTicket(int cartId, int eventId, int quantity) {
        try {
            Optional<Cart> cartOptional = cartService.findById(cartId);
            Optional<Event> eventOptional = eventService.findById(eventId);

            if (cartOptional.isPresent() && eventOptional.isPresent()) {
                Cart cart = cartOptional.get();
                Event event = eventOptional.get();

                Ticket ticket = new Ticket();
                ticket.setCart(cart);
                ticket.setEvent(event);
                ticket.setQuantity(quantity);
                ticket.setCreatedDate(LocalDateTime.now());
                ticket.setExpiredDate(event.getTimeend());
                ticket.setStatus(Ticket.Status.PENDING);

                return Optional.of(ticketRepository.save(ticket));
            } else {
                System.err.println("Cart or Event not found for given IDs.");
                return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Error occurred while creating the ticket: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Map<String, Object>> countPaidTicketsByEventId(int eventId) {
        long count = ticketRepository.countPaidTicketsByEventId(eventId);
        Optional<Event> eventOptional = eventService.findById(eventId);
        Map<String, Object> response = new HashMap<>();
        if (eventOptional.isPresent()) {
            response.put("Amount: ", count);
        } else {
            response.put("error", "Event not found");
        }
        return Optional.of(response);
    }

    public List<Object> findVisitorsByEventIdAndStatusPaid(int eventId) {
        List<Visitor> visitors = ticketRepository.findVisitorsByEventIdAndStatusPaid(eventId);
        if (visitors == null || visitors.isEmpty()) {
            return Collections.singletonList("Data is null"); // Return a list with a message
        }
        return Collections.singletonList(visitors);
    }

    public Optional<Map<String, Object>> viewTicketsByEventAndDate(int eventId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Ticket> tickets = ticketRepository.findByEvent_IdAndCreatedDateBetween(eventId, startDate, endDate);
        Optional<Event> eventOptional = eventService.findById(eventId);

        if (!eventOptional.isPresent()) {
            return Optional.empty();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("startDate", startDate);
        response.put("endDate", endDate);
        response.put("amount", tickets.size());

        List<Map<String, Object>> visitorList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Map<String, Object> visitorInfo = new HashMap<>();
            Visitor visitor = ticket.getCart().getVisitor();
            visitorInfo.put("visitorId", visitor.getId());
            visitorList.add(visitorInfo);
        }
        response.put("visitorList", visitorList);

        return Optional.of(response);
    }
}
