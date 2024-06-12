package EventManagement.Event.repository;

import EventManagement.Event.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
}
