package EventManagement.Event.repository;

import EventManagement.Event.entity.SponsorEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorEventRepository extends JpaRepository<SponsorEvent, Integer> {
}
