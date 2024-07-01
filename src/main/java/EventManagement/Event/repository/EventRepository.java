package EventManagement.Event.repository;

import EventManagement.Event.entity.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByAccountId(int accountId);
    List<Event> findByStateEventId(int stateEventId);
    @Query("SELECT e FROM event e WHERE e.checkingStaff.id = :checkingStaffId")
    List<Event> findByCheckingStaff(@Param("checkingStaffId") Long checkingStaffId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sponsor_program_event WHERE event_id = :eventId", nativeQuery = true)
    void deleteSponsorProgramEventByEventId(int eventId);
}