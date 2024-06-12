package EventManagement.Event.repository;

import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor,Long> {
}
