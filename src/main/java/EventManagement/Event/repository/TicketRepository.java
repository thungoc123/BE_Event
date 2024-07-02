package EventManagement.Event.repository;

import EventManagement.Event.entity.Ticket;
import EventManagement.Event.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByCart_CartId(int cartId);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.event.id = :eventId AND t.status = EventManagement.Event.entity.Ticket.Status.PAID")
    long countPaidTicketsByEventId(int eventId);

    @Query("SELECT t.cart.visitor FROM Ticket t WHERE t.event.id = :eventId AND t.status = EventManagement.Event.entity.Ticket.Status.PAID")
    List<Visitor> findVisitorsByEventIdAndStatusPaid(int eventId);
}
