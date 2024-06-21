package EventManagement.Event.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "feedback_answer")
public class FeedbackAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackAnswerID;

    private String answer;

    private LocalDateTime deletedAt;

    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "feedbackQuestionID", nullable = false)
    private FeedbackQuestion feedbackQuestion;

    @OneToMany(mappedBy = "feedbackAnswer")
    private Set<VisitorAnswer> visitorAnswers;

    public int getFeedbackAnswerID() {
        return feedbackAnswerID;
    }

    public void setFeedbackAnswerID(int feedbackAnswerID) {
        this.feedbackAnswerID = feedbackAnswerID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public FeedbackQuestion getFeedbackQuestion() {
        return feedbackQuestion;
    }

    public void setFeedbackQuestion(FeedbackQuestion feedbackQuestion) {
        this.feedbackQuestion = feedbackQuestion;
    }

    public Set<VisitorAnswer> getVisitorAnswers() {
        return visitorAnswers;
    }

    public void setVisitorAnswers(Set<VisitorAnswer> visitorAnswers) {
        this.visitorAnswers = visitorAnswers;
    }
}
