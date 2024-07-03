package EventManagement.Event.DTO;

import EventManagement.Event.entity.Event;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class FeedbackQuestionDTO {
    private int feedbackQuestionID;
    private String typeQuestion;
    private String textQuestion;
    private LocalDateTime deletedAt;
    private LocalDateTime modifiedAt;
    private int feedbackID;
//    private int eventID; // Thêm trường eventID
//    private String eventName; // Thêm trường eventName
//    private String description;
//    private LocalDateTime timecloseasle;
//    private LocalDateTime timeend;
//    private LocalDateTime timeopensale;
//    private LocalDateTime timestart;
    private EventDTO event; // Thay vì lưu các thuộc tính riêng lẻ, bạn có thể sử dụng một đối tượng EventDTO

    // Các getter và setter


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

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }
}
