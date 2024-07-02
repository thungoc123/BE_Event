package EventManagement.Event.DTO;

import EventManagement.Event.entity.State;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackEventDTO {
    private int feedbackID;
    private String title;
    private String eventName;
    private State state;
    private LocalDateTime modifiedAt;

    public FeedbackEventDTO(int feedbackID, String title, String eventName, State state, LocalDateTime modifiedAt) {
        this.feedbackID = feedbackID;
        this.title = title;
        this.eventName = eventName;
        this.state = state;
        this.modifiedAt = modifiedAt;
    }

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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}



