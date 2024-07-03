package EventManagement.Event.repository;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Feedback;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByAccountId(int accountId);
    List<Event> findByStateEventId(int stateEventId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sponsor_event WHERE event_id = :eventId"
            , nativeQuery = true)
    void deleteBySponsorByEventId(int eventId);
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM feedback WHERE event_id = :eventId; " +
//            "DELETE FROM feedback_question WHERE feedbackid IN " +
//            "(SELECT feedbackid FROM feedback WHERE event_id = :eventId); " +
//            "DELETE FROM visitor_answer WHERE feedback_question_id IN " +
//            "(SELECT fq.feedback_question_id FROM feedback_question fq " +
//            "INNER JOIN feedback f ON fq.feedbackid = f.feedbackid " +
//            "WHERE f.event_id = :eventId)",
//            nativeQuery = true)
//    void deleteFeedbackByEventId(int eventId);



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sponsor_program_event WHERE event_id = :eventId", nativeQuery = true)
    void deleteSponsorProgramEventByEventId(int eventId);
//    @Query("SELECT DISTINCT e FROM Event e JOIN e.feedbacks f WHERE f = :feedback")
//    List<Event> findByFeedback(@Param("feedback") Feedback feedback);

}