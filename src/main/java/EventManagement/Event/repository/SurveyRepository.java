package EventManagement.Event.repository;

import EventManagement.Event.DTO.FeedbackEventDTO;
import EventManagement.Event.DTO.SurveyDTO;
import EventManagement.Event.DTO.SurveyEventDTO;
import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
//    @Query("SELECT s FROM Survey s JOIN s.event e JOIN e.account a WHERE a.id = :accountId")
//    List<Survey> findByAccountId(@Param("accountId") int accountId);

    @Query("SELECT new EventManagement.Event.DTO.SurveyEventDTO(s.surveyId, s.title, s.target, s.modifiedAt, s.deleteAt, e.name) " +
            "FROM Survey s JOIN s.event e JOIN e.account a WHERE a.id = :accountId")
    List<SurveyEventDTO> findByAccountId(@Param("accountId") int accountId);

}
