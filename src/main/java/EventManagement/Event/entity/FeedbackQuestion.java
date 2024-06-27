package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(mappedBy = "feedbackQuestion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<FeedbackAnswer> feedbackAnswers;

    public int getFeedbackQuestionID() {
        return feedbackQuestionID;
    }

    public void setFeedbackQuestionID(int feedbackQuestionID) {
        this.feedbackQuestionID = feedbackQuestionID;
    }

    public String getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(String typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Set<FeedbackAnswer> getFeedbackAnswers() {
        return feedbackAnswers;
    }

    public void setFeedbackAnswers(Set<FeedbackAnswer> feedbackAnswers) {
        this.feedbackAnswers = feedbackAnswers;
    }
}
