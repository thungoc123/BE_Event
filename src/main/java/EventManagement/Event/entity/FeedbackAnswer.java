package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "feedback_answer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "feedbackAnswerID")
public class FeedbackAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackAnswerID;

    private String answer;

    private LocalDateTime deletedAt;

    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "feedbackQuestionID", nullable = false)
    @JsonIgnoreProperties("feedbackAnswers") // Ignore feedbackAnswers property in FeedbackQuestion
    private FeedbackQuestion feedbackQuestion;

    @OneToMany(mappedBy = "feedbackAnswer")
    @JsonIgnoreProperties("feedbackAnswer") // Assuming VisitorAnswer entity needs to be handled similarly
    private Set<VisitorAnswer> visitorAnswers;

    // constructors, getters, setters, etc.
}
