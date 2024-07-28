package EventManagement.Event.repository;

import EventManagement.Event.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion,Long> {
    @Query("SELECT sq FROM SurveyQuestion sq WHERE sq.survey.surveyId = :surveyId")
    List<SurveyQuestion> findBySurveyId(@Param("surveyId") int surveyId);
}
