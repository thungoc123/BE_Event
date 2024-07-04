package EventManagement.Event.repository;

import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.SponsorProgram;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SponsorProgramRepository extends JpaRepository<SponsorProgram, Integer> {
    boolean existsByTitle(String title);
    List<SponsorProgram> findByAccountId(int accountId);
    List<SponsorProgram> findByEvents_Id(int eventId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sponsor_program_event WHERE sponsor_program_id = :sponsorProgramId", nativeQuery = true)
    void deleteSponsorProgramEventBySponsorProgramId( int sponsorProgramId);

}
