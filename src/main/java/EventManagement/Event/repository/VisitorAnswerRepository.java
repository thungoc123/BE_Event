package EventManagement.Event.repository;

import EventManagement.Event.entity.FeedbackAnswer;
import EventManagement.Event.entity.VisitorAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorAnswerRepository extends JpaRepository<VisitorAnswer, Integer> {
    //long countByFeedbackAnswer(FeedbackAnswer feedbackAnswer);
    void deleteByFeedbackAnswer(FeedbackAnswer feedbackAnswer);
    long countByFeedbackAnswer(FeedbackAnswer feedbackAnswer);
}