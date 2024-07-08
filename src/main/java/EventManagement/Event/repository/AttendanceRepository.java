package EventManagement.Event.repository;

import EventManagement.Event.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findAllByTicket_Id(int ticketId);
    List<Attendance> findAll();
    List<Attendance> findByEventId(int eventId);
}


