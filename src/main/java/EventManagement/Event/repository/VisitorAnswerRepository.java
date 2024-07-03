package EventManagement.Event.repository;


import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.entity.VisitorAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitorAnswerRepository extends JpaRepository<VisitorAnswer, Integer> {
    //long countByFeedbackAnswer(FeedbackAnswer feedbackAnswer);
    List<VisitorAnswer> findByFeedbackQuestion_Feedback_FeedbackIDAndVisitor_Id(int feedbackId, int visitorId);
    List<VisitorAnswer> findByFeedbackQuestion_Feedback_FeedbackID(int feedbackId);

}