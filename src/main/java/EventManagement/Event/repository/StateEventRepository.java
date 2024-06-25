package EventManagement.Event.repository;

import EventManagement.Event.entity.StateEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateEventRepository extends JpaRepository<StateEvent, Integer> {
    StateEvent findById(int id);
}
