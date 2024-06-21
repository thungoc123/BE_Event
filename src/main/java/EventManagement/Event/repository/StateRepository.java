package EventManagement.Event.repository;

import EventManagement.Event.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State,Integer> {
}
