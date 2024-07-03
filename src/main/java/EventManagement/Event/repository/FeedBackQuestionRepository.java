package EventManagement.Event.repository;

import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.FeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedBackQuestionRepository extends JpaRepository<FeedbackQuestion, Integer> {
    void deleteById(int feedbackQuestionID);
    List<FeedbackQuestion> findByFeedback(Feedback feedback);
    List<FeedbackQuestion> findByFeedback_FeedbackID(int feedbackId);


}
