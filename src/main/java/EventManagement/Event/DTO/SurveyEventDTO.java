package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyEventDTO {
    private Long surveyId;
    private String title;
    private String target;
    private LocalDateTime modifiedAt;
    private LocalDateTime deleteAt;
    private String eventName;

    // Constructor with parameters matching the query
    public SurveyEventDTO(Long surveyId, String title, String target, LocalDateTime modifiedAt, LocalDateTime deleteAt, String eventName) {
        this.surveyId = surveyId;
        this.title = title;
        this.target = target;
        this.modifiedAt = modifiedAt;
        this.deleteAt = deleteAt;
        this.eventName = eventName;
    }

    // Getters and setters
    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}