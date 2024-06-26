package EventManagement.Event.DTO;

import lombok.Data;

@Data
public class FeedbackAnswerDetailsDTO {
    private int answerId;
    private String answer;
    private int feedbackQuestionId;
    private String textQuestion;
    private long visitorCount;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public int getFeedbackQuestionId() {
        return feedbackQuestionId;
    }

    public void setFeedbackQuestionId(int feedbackQuestionId) {
        this.feedbackQuestionId = feedbackQuestionId;
    }

    public long getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(long visitorCount) {
        this.visitorCount = visitorCount;
    }
}
