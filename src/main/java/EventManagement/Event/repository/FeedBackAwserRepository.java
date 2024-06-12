package EventManagement.Event.repository;

import EventManagement.Event.entity.FeedbackAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  FeedBackAwserRepository extends JpaRepository<FeedbackAnswer,Integer> {
}
