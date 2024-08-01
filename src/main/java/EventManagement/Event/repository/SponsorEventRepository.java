package EventManagement.Event.repository;

import EventManagement.Event.DTO.SponsorProfitDTO;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.entity.SponsorEvent;
import EventManagement.Event.entity.SponsorEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SponsorEventRepository extends JpaRepository<SponsorEvent, SponsorEventId> {
    List<SponsorEvent> findBySponsorId(Long sponsorId);
    List<SponsorEvent> findByEventAndSponsor(Event event, Sponsor sponsor);
    List<SponsorEvent> findByEventId(int eventId);




}
