package EventManagement.Event.payload.Request;

public class CreateVisitorAnswerRequest {
    private int visitorId;
    private int feedbackQuestionId;
    private String visitorAnswerFeedback;

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
