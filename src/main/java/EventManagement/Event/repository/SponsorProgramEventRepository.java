package EventManagement.Event.repository;

import EventManagement.Event.entity.SponsorEventId;
import EventManagement.Event.entity.SponsorProgramEvent;
import EventManagement.Event.entity.SponsorProgramEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorProgramEventRepository extends JpaRepository<SponsorProgramEvent, SponsorProgramEventId> {
}
