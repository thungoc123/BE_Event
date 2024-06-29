package EventManagement.Event.repository;

import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.FeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackQuestionRepository extends JpaRepository<FeedbackQuestion, Integer> {
    void deleteById(int feedbackQuestionID);
    List<FeedbackQuestion> findByFeedback(Feedback feedback);
}
