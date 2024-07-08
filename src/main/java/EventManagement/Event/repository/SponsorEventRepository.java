package EventManagement.Event.repository;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.entity.SponsorEvent;
import EventManagement.Event.entity.SponsorEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorEventRepository extends JpaRepository<SponsorEvent, SponsorEventId> {
    List<SponsorEvent> findBySponsorId(Long sponsorId);
    List<SponsorEvent> findByEventAndSponsor(Event event, Sponsor sponsor);

}
