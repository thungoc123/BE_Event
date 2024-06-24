package EventManagement.Event.repository;

import EventManagement.Event.entity.EventOperator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOperatorRepository extends JpaRepository<EventOperator, Integer> {
}
