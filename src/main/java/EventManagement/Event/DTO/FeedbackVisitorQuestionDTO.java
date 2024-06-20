package EventManagement.Event.DTO;

public class FeedbackVisitorQuestionDTO {
    private int feedbackQuestionID;
    private String textQuestion;
    private int feedbackAnswerID;
    private String answer;
    private long visitorCount;

    // Constructors, getters, and setters
    public FeedbackVisitorQuestionDTO(int feedbackQuestionID, String textQuestion, int feedbackAnswerID, String answer, long visitorCount) {
        this.feedbackQuestionID = feedbackQuestionID;
        this.textQuestion = textQuestion;
        this.feedbackAnswerID = feedbackAnswerID;
        this.answer = answer;
        this.visitorCount = visitorCount;
    }

    public int getFeedbackQuestionID() {
        return feedbackQuestionID;
    }

    public void setFeedbackQuestionID(int feedbackQuestionID) {
        this.feedbackQuestionID = feedbackQuestionID;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

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

    public long getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(long visitorCount) {
        this.visitorCount = visitorCount;
    }
}
