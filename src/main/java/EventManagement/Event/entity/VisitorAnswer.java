package EventManagement.Event.entity;

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
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "feedback_answer_id", nullable = false)
    private FeedbackAnswer feedbackAnswer;

    private String visitorAnswerFeedback;

    private int visitorCount;
}
