package EventManagement.Event.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_answer")
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerID;

    @Column(name = "answer")
    private String answer;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private SurveyQuestion surveyQuestion;

    // Constructors
    public SurveyAnswer() {
    }

    public SurveyAnswer(String answer, LocalDateTime modifiedAt, LocalDateTime deleteAt, SurveyQuestion surveyQuestion) {
        this.answer = answer;
        this.modifiedAt = modifiedAt;
        this.deleteAt = deleteAt;
        this.surveyQuestion = surveyQuestion;
    }

    // Getters and Setters
    public Long getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Long answerID) {
        this.answerID = answerID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public SurveyQuestion getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }
}
