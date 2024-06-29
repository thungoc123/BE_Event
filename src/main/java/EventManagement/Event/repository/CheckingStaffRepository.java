package EventManagement.Event.repository;


import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.entity.SponsorProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingStaffRepository extends JpaRepository<CheckingStaff, Integer> {
    List<CheckingStaff> findByEventId(int eventId);
}
