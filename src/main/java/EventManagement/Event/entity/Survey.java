package EventManagement.Event.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long surveyId;

    @Column(name = "title")
    private String title;

    @Column(name = "target")
    private String target;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SurveyQuestion> surveyQuestions;

    // Constructors, getters, setters
}
