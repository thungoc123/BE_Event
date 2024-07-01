package EventManagement.Event.repository;

import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.entity.EventSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventScheduleRepository extends JpaRepository<EventSchedule, Integer> {
    List<EventSchedule> findByEventId(int eventId);
}