package EventManagement.Event.service;

import EventManagement.Event.entity.Cart;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.entity.Visitor;
import EventManagement.Event.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            // Log the exception and handle it as needed
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
                ticket.setStatus(Ticket.Status.PENDING); // Set initial status to PENDING

                return Optional.of(ticketRepository.save(ticket));
            } else {
                // Log or handle the case where cart or event is not found
                System.err.println("Cart or Event not found for given IDs.");
                return Optional.empty();
            }
        } catch (Exception e) {
            // Log the exception and handle it as needed
            System.err.println("Error occurred while creating the ticket: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public long countPaidTicketsByEventId(int eventId) {
        return ticketRepository.countPaidTicketsByEventId(eventId);
    }

    public List<Visitor> findVisitorsByEventIdAndStatusPaid(int eventId) {
        return ticketRepository.findVisitorsByEventIdAndStatusPaid(eventId);
    }
}
