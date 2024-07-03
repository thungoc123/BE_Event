package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "feedback_question")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "feedbackQuestionID")
public class FeedbackQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackQuestionID;



    private String textQuestion;

    private LocalDateTime deletedAt;

    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "feedbackID", nullable = false)
    @JsonIgnoreProperties("feedbackQuestions")
    private Feedback feedback;

    @OneToMany(mappedBy = "feedbackQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("feedbackQuestion")
    private Set<VisitorAnswer> visitorAnswers;








}
