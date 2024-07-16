package EventManagement.Event.service;

import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.entity.Visitor;
import EventManagement.Event.repository.TicketRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VisitorService visitorService;

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

    public boolean deleteTicket(int id) {
        Optional<Ticket> ticketOptional = findById(id);
        if (ticketOptional.isPresent()) {
            deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Ticket> updateTicketStatus(int id, Ticket.Status status) {
        try {
            Optional<Ticket> ticketOptional = findById(id);
            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
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

    public Optional<Map<String, String>> createOrderTicket(Integer visitorId, Integer eventId, boolean statusCart, Ticket.Status status) {
        Map<String, String> response = new HashMap<>();

        try {
            Optional<Visitor> visitorOptional = visitorService.findById(visitorId);
            Optional<Event> eventOptional = eventService.findById(eventId);

            if (visitorOptional.isPresent() && eventOptional.isPresent()) {
                Visitor visitor = visitorOptional.get();
                Event event = eventOptional.get();

                // Check if the visitor already has a ticket for this event
                Optional<Ticket> existingTicket = ticketRepository.findByVisitor_IdAndEvent_Id(visitorId, eventId);
                if (existingTicket.isPresent()) {
                    response.put("message", "Visitor already has a ticket for this event.");
                    return Optional.of(response);
                }

                if (LocalDateTime.now().isBefore(event.getTimeclosesale())) {
                    Ticket ticket = new Ticket();
                    ticket.setVisitor(visitor);
                    ticket.setEvent(event);
                    ticket.setCreatedDate(LocalDateTime.now());
                    ticket.setExpiredDate(event.getTimeclosesale());
                    ticket.setStatus(status); // Use the provided status
                    ticket.setStatusCart(statusCart);

                    ticketRepository.save(ticket);
                    response.put("message", "Ticket created successfully");
                    return Optional.of(response);
                } else {
                    response.put("message", "Cannot create ticket for expired event.");
                    return Optional.of(response);
                }
            } else {
                response.put("message", "Visitor or Event not found for given IDs.");
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
        return new ArrayList<>(visitors);
    }

    public Optional<Map<String, Object>> viewTicketsByEventAndDate(int eventId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Ticket> tickets = ticketRepository.findByEvent_IdAndCreatedDateBetween(eventId, startDate, endDate);
        Optional<Event> eventOptional = eventService.findById(eventId);

        if (!eventOptional.isPresent()) {
            return Optional.empty();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        Map<String, Object> response = new HashMap<>();
        response.put("startDate", startDate.format(formatter));
        response.put("endDate", endDate.format(formatter));
        response.put("amount", tickets.size());

        List<Map<String, Object>> visitorList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Map<String, Object> visitorInfo = new HashMap<>();
            Visitor visitor = ticket.getVisitor();
            visitorInfo.put("visitorId", visitor.getId());
            visitorList.add(visitorInfo);
        }
        response.put("visitorList", visitorList);

        return Optional.of(response);
    }

    public Optional<Map<String, Object>> countTicketsByEventIdAndDate(int eventId, LocalDateTime dateTime) {
        Optional<Event> eventOptional = eventService.findById(eventId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            LocalDate date = dateTime.toLocalDate();
            if (date.isAfter(event.getTimeopensale().toLocalDate()) && date.isBefore(event.getTimeclosesale().toLocalDate())) {
                long count = ticketRepository.countTicketsByEventIdAndDate(eventId, date);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                Map<String, Object> response = new HashMap<>();
                response.put("Date", dateTime.format(formatter));
                response.put("Amount Ticket on Date", count);
                return Optional.of(response);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public Map<String, Object> getVisitorEmailAndTicketPrice(int ticketId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);

        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            Visitor visitor = ticket.getVisitor();
            Account account = visitor.getAccount();
            String email = account.getEmail();
            double price = ticket.getPrice();
            int visitorId = visitor.getId(); // Get the visitor ID

            response.put("email", email);
            response.put("price", price);
            response.put("visitorId", visitorId); // Add the visitor ID to the response
        } else {
            response.put("message", "Ticket not found");
        }

        return response;
    }

    public List<Ticket> getTicketsByStatusAndCart(Ticket.Status status) {
        return ticketRepository.findByStatusAndStatusCart(status, true);
    }

    public List<Ticket> getTicketsInCart() {
        return ticketRepository.findByStatusCart(true);
    }

    public Optional<Map<String, Object>> findVisitorIdByAccountId(int accountId) {
        Optional<Visitor> visitorOptional = visitorService.findByAccountId(accountId);
        if (visitorOptional.isPresent()) {
            Visitor visitor = visitorOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("visitorId", visitor.getId());
            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }
}
