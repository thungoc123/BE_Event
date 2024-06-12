package EventManagement.Event.repository;

import EventManagement.Event.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor,Long> {
}
