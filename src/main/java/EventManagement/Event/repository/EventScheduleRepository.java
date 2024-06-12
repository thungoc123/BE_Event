package EventManagement.Event.repository;

import EventManagement.Event.entity.EventSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventScheduleRepository extends JpaRepository<EventSchedule, Integer> {
}