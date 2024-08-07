package EventManagement.Event.repository;


import EventManagement.Event.entity.CheckingStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CheckingStaffRepository extends JpaRepository<CheckingStaff, Integer> {
   CheckingStaff findFirstByAccountId(int accountId);
   List<CheckingStaff> findByEventId(int eventId);
   List<CheckingStaff> findByAccountId(int id);
}
