package EventManagement.Event.repository;

import EventManagement.Event.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    List<Feedback> findByAccount_Id(Long accountId);
}

