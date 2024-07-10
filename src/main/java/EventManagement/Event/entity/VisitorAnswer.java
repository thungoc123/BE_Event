package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "visitor_answer")
public class VisitorAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    @JsonIgnoreProperties("visitorAnswers")
    private Visitor visitor;

    private String visitorAnswerFeedback;

    @ManyToOne
    @JoinColumn(name = "feedback_question_id", nullable = false)
    @JsonIgnoreProperties("visitorAnswers")
    private FeedbackQuestion feedbackQuestion;
}
