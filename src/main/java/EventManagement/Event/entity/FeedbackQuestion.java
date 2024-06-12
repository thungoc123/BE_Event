package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "feedback_question")
public class FeedbackQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackQuestionID;

    private String typeQuestion;

    private String textQuestion;

    private LocalDateTime deletedAt;

    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "feedbackID", nullable = false)
    private Feedback feedback;
}
