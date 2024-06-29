package EventManagement.Event.repository;

import EventManagement.Event.entity.FeedbackAnswer;
import EventManagement.Event.entity.FeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  FeedBackAwserRepository extends JpaRepository<FeedbackAnswer,Integer> {
    List<FeedbackAnswer> findByFeedbackQuestion(FeedbackQuestion feedbackQuestion);
}
