package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitorAnswerDTO {
    private int id;
    private int visitorId;
    private int feedbackQuestionId;
    private String visitorAnswerFeedback;


    // Constructors, getters, and setters
    public VisitorAnswerDTO() {
    }

    public VisitorAnswerDTO(int id, int visitorId, int feedbackQuestionId, String visitorAnswerFeedback) {
        this.id = id;
        this.visitorId = visitorId;
        this.feedbackQuestionId = feedbackQuestionId;
        this.visitorAnswerFeedback = visitorAnswerFeedback;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public int getFeedbackQuestionId() {
        return feedbackQuestionId;
    }

    public void setFeedbackQuestionId(int feedbackQuestionId) {
        this.feedbackQuestionId = feedbackQuestionId;
    }

    public String getVisitorAnswerFeedback() {
        return visitorAnswerFeedback;
    }

    public void setVisitorAnswerFeedback(String visitorAnswerFeedback) {
        this.visitorAnswerFeedback = visitorAnswerFeedback;
    }


}
