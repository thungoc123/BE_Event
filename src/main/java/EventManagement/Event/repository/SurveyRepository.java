package EventManagement.Event.repository;

import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
