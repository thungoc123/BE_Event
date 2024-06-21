package EventManagement.Event.repository;

import EventManagement.Event.entity.FeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackQuestionRepository extends JpaRepository<FeedbackQuestion, Integer> {
    void deleteById(int feedbackQuestionID);
}
