package EventManagement.Event.repository;

import EventManagement.Event.DTO.FeedbackEventDTO;
import EventManagement.Event.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    void deleteByFeedbackID(int feedbackID);
    List<Feedback> findByEvent_Id(int eventID);
    List<Feedback> findByEvent_Account_Id(int accountID);
    @Query("SELECT new EventManagement.Event.DTO.FeedbackEventDTO(f.feedbackID, f.title, e.name, f.state, f.modifiedAt) " +
            "FROM Feedback f JOIN f.event e WHERE e.account.id = :accountID")
    List<FeedbackEventDTO> findFeedbacksWithEventNameAndStateByAccountID(int accountID);

}

