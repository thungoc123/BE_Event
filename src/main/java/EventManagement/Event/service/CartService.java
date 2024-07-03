package EventManagement.Event.service;

import EventManagement.Event.entity.Cart;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.repository.CartRepository;
import EventManagement.Event.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Optional<Cart> findById(int id) {
        return cartRepository.findById(id);
    }

    public Optional<Cart> findByVisitorId(int visitorId) {
        return cartRepository.findByVisitorId(visitorId);
    }

    public List<Ticket> findTicketsByCartId(int cartId) {
        return ticketRepository.findByCart_CartId(cartId);
    }

    public Optional<Ticket> findTicketByCartIdAndTicketId(int cartId, int ticketId) {
        return ticketRepository.findByIdAndCart_CartId(ticketId, cartId);
    }

    public long countTicketsByCartId(int cartId) {
        return ticketRepository.countByCart_CartId(cartId);
    }

    public Map<Ticket.Status, List<Ticket>> findTicketsByCartIdGroupedByStatus(int cartId) {
        List<Ticket> tickets = ticketRepository.findByCart_CartId(cartId);
        Map<Ticket.Status, List<Ticket>> ticketsByStatus = new HashMap<>();
        for (Ticket ticket : tickets) {
            ticketsByStatus.computeIfAbsent(ticket.getStatus(), k -> new ArrayList<>()).add(ticket);
        }
        return ticketsByStatus;
    }
}
