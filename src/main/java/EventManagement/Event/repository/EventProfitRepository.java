package EventManagement.Event.repository;

import EventManagement.Event.entity.EventProfit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventProfitRepository extends JpaRepository<EventProfit, Integer> {
    Optional<EventProfit> findByEventId(int eventId);
}
