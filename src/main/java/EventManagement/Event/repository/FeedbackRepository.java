package EventManagement.Event.repository;

import EventManagement.Event.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    void deleteByFeedbackID(int feedbackID);
    List<Feedback> findByEvent_Id(int eventID);
    List<Feedback> findByEvent_Account_Id(int accountID);
}

