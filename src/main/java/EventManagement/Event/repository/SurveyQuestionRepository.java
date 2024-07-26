package EventManagement.Event.repository;

import EventManagement.Event.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion,Long> {
}
