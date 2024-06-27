package EventManagement.Event.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "survey_question")
public class SurveyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "type_question")
    private String typeQuestion;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(mappedBy = "surveyQuestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SurveyAnswer> surveyAnswers;

    // Constructors, getters, setters
}
