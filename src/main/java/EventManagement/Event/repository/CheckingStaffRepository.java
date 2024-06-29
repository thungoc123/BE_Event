package EventManagement.Event.repository;


import EventManagement.Event.entity.CheckingStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingStaffRepository extends JpaRepository<CheckingStaff, Integer> {
}
