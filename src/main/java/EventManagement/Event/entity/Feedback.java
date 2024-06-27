package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackID;

    private String title;

    private LocalDateTime deleteAt;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private State state;

    @OneToMany(mappedBy = "feedback", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedbackQuestion> feedbackQuestions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties("feedbacks")
    private Account account;


}
