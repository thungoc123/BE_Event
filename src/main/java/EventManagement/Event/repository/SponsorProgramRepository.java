package EventManagement.Event.repository;

import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.SponsorProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SponsorProgramRepository extends JpaRepository<SponsorProgram, Integer> {
    boolean existsByTitle(String title);
    List<SponsorProgram> findByAccountId(int accountId);
    List<SponsorProgram> findByEvents_Id(int eventId);

}
