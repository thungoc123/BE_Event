package EventManagement.Event.repository;

import EventManagement.Event.entity.Ticket;
import EventManagement.Event.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.event.id = :eventId AND t.status = EventManagement.Event.entity.Ticket.Status.PAID")
    long countPaidTicketsByEventId(int eventId);
    @Query("SELECT t.visitor FROM Ticket t WHERE t.event.id = :eventId AND t.status = EventManagement.Event.entity.Ticket.Status.PAID")
    List<Visitor> findVisitorsByEventIdAndStatusPaid(int eventId);
    List<Ticket> findByEvent_IdAndCreatedDateBetween(int eventId, LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.event.id = :eventId AND DATE(t.createdDate) = :date")
    long countTicketsByEventIdAndDate(@Param("eventId") int eventId, @Param("date") LocalDate date);
//    Optional<Ticket> findByVisitor_IdAndEvent_Id(int visitorId, int eventId);
    List<Ticket> findByEvent_Id(int eventId);
    List<Ticket> findByStatusAndStatusCart(Ticket.Status status, boolean statusCart);
    List<Ticket> findByStatusCart(boolean statusCart);
    @Query("SELECT COUNT(DISTINCT t.visitor) FROM Ticket t WHERE t.event.id = :eventId")
    long countDistinctByEvent_Id(@Param("eventId") int eventId);
    List<Ticket> findByVisitor_IdAndEvent_Id(int visitorId, int eventId);
    @Query("SELECT t FROM Ticket t WHERE t.visitor.id = :visitorId AND t.statusCart = true")
    List<Ticket> findByVisitorIdAndStatusCart(@Param("visitorId") int visitorId);
}
