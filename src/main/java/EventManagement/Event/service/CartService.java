package EventManagement.Event.service;

import EventManagement.Event.entity.Cart;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.repository.CartRepository;
import EventManagement.Event.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
