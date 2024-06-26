package EventManagement.Event.repository;

import EventManagement.Event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByAccountId(int accountId);
    List<Event> findByStateEventId(int stateEventId);
}