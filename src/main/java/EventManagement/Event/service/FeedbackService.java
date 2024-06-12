package EventManagement.Event.service;

import EventManagement.Event.DTO.FeedbackAnswerDTO;
import EventManagement.Event.DTO.FeedbackDTO;
import EventManagement.Event.DTO.FeedbackQuestionDTO;
import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.FeedbackAnswer;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.FeedBackAwserRepository;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedBackAwserRepository feedBackAwserRepository;
    @Autowired
    private FeedBackQuestionRepository feedBackQuestionRepository;
    @Autowired
    private AccountRepository accountRepository;


    @Transactional
    public Feedback createFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setTitle(feedbackDTO.getTitle());
        feedback.setDeleteAt(feedbackDTO.getDeletedAt());
        feedback.setModifiedAt(feedbackDTO.getModifiedAt());

        Feedback savedFeedback = feedbackRepository.save(feedback);

        List<FeedbackQuestionDTO> questions = feedbackDTO.getQuestions();
        if (questions != null) {
            for (FeedbackQuestionDTO questionDTO : questions) {
                FeedbackQuestion question = new FeedbackQuestion();
                question.setTypeQuestion(questionDTO.getTypeQuestion());
                question.setTextQuestion(questionDTO.getTextQuestion());
                question.setDeletedAt(questionDTO.getDeletedAt());
                question.setModifiedAt(questionDTO.getModifiedAt());
                question.setFeedback(savedFeedback);

                FeedbackQuestion savedQuestion = feedBackQuestionRepository.save(question);

                List<FeedbackAnswerDTO> answers = questionDTO.getAnswers();
                if (answers != null) {
                    for (FeedbackAnswerDTO answerDTO : answers) {
                        FeedbackAnswer answer = new FeedbackAnswer();
                        answer.setAnswer(answerDTO.getAnswer());
                        answer.setDeletedAt(answerDTO.getDeletedAt());
                        answer.setModifiedAt(answerDTO.getModifiedAt());
                        answer.setFeedbackQuestion(savedQuestion);

                        feedBackAwserRepository.save(answer);
                    }
                }
            }
        }

        return savedFeedback;
    }
    public Account findByEmail(String email){
     return    accountRepository.findByEmail(email);
    }
}