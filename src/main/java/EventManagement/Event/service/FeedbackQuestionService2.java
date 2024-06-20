package EventManagement.Event.service;


import EventManagement.Event.DTO.FeedbackVisitorQuestionDTO;
import EventManagement.Event.entity.FeedbackAnswer;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.repository.FeedBackAwserRepository;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.repository.VisitorAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackQuestionService2 {

    @Autowired
    private FeedBackQuestionRepository feedbackQuestionRepository;

    @Autowired
    private FeedBackAwserRepository feedbackAnswerRepository;

    @Autowired
    private VisitorAnswerRepository visitorAnswerRepository;

    public List<FeedbackVisitorQuestionDTO> getAll() {
        List<FeedbackQuestion> feedbackQuestions = feedbackQuestionRepository.findAll();
        List<FeedbackAnswer> feedbackAnswers = feedbackAnswerRepository.findAll();
        
        List<FeedbackVisitorQuestionDTO> result = new ArrayList<>();

        for (FeedbackQuestion feedbackQuestion : feedbackQuestions) {

                for (FeedbackAnswer feedbackAnswer : feedbackAnswers) {
                    long visitorCount = visitorAnswerRepository.countByFeedbackAnswer(feedbackAnswer);
                    FeedbackVisitorQuestionDTO dto = new FeedbackVisitorQuestionDTO(
                            feedbackQuestion.getFeedbackQuestionID(),
                            feedbackQuestion.getTextQuestion(),
                            feedbackAnswer.getFeedbackAnswerID(),
                            feedbackAnswer.getAnswer(),
                            visitorCount
                    );
                    result.add(dto);
                }


        }

        return result;
    }
}