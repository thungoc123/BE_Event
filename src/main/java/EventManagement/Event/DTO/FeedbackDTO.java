package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class FeedbackDTO {
    private int feedbackID;
    private String title;
    private LocalDateTime deletedAt;
    private LocalDateTime modifiedAt;
    private int stateID;

    private List<FeedbackQuestionDTO> feedbackQuestions;

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public List<FeedbackQuestionDTO> getFeedbackQuestions() {
        return feedbackQuestions;
    }

    public void setFeedbackQuestions(List<FeedbackQuestionDTO> feedbackQuestions) {
        this.feedbackQuestions = feedbackQuestions;
    }
}

