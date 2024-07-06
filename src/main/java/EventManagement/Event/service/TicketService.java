package EventManagement.Event.service;

import EventManagement.Event.entity.Cart;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.entity.Visitor;
import EventManagement.Event.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Optional<Map<String, String>> createOrderTicket(int cartId, int eventId, int quantity) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<Cart> cartOptional = cartService.findById(cartId);
            Optional<Event> eventOptional = eventService.findById(eventId);

            if (cartOptional.isPresent() && eventOptional.isPresent()) {
                Cart cart = cartOptional.get();
                Event event = eventOptional.get();

                if (LocalDateTime.now().isBefore(event.getTimeclosesale())) {
                    Ticket ticket = new Ticket();
                    ticket.setCart(cart);
                    ticket.setEvent(event);
                    ticket.setQuantity(quantity);
                    ticket.setCreatedDate(LocalDateTime.now());
                    ticket.setExpiredDate(event.getTimeclosesale());
                    ticket.setStatus(Ticket.Status.PENDING);

                    ticketRepository.save(ticket);
                    response.put("message", "Ticket created successfully");
                    return Optional.of(response);
                } else {
                    response.put("message", "Cannot create ticket for expired event.");
                    return Optional.of(response);
                }
            } else {
                response.put("message", "Cart or Event not found for given IDs.");
                return Optional.of(response);
            }
        } catch (Exception e) {
            response.put("message", "Error occurred while creating the ticket: " + e.getMessage());
            e.printStackTrace();
            return Optional.of(response);
        }
    }

    public Optional<Map<String, Object>> countPaidTicketsByEventId(int eventId) {
        long count = ticketRepository.countPaidTicketsByEventId(eventId);
        Optional<Event> eventOptional = eventService.findById(eventId);
        Map<String, Object> response = new HashMap<>();
        if (eventOptional.isPresent()) {
            response.put("Amount", count);
        } else {
            response.put("error", "Event not found");
        }
        return Optional.of(response);
    }

    public List<Object> findVisitorsByEventIdAndStatusPaid(int eventId) {
        List<Visitor> visitors = ticketRepository.findVisitorsByEventIdAndStatusPaid(eventId);
        if (visitors == null || visitors.isEmpty()) {
            return Collections.singletonList("Data is null");
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

    public Optional<Map<String, Object>> countTicketsByEventIdAndDate(int eventId, LocalDate date) {
        Optional<Event> eventOptional = eventService.findById(eventId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (date.isAfter(event.getTimeopensale().toLocalDate()) && date.isBefore(event.getTimeclosesale().toLocalDate())) {
                long count = ticketRepository.countTicketsByEventIdAndDate(eventId, date);
                Map<String, Object> response = new HashMap<>();
                response.put("Date", date);
                response.put("Amount Ticket on Date", count);
                return Optional.of(response);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
