package EventManagement.Event.service;


import EventManagement.Event.DTO.FeedbackVisitorQuestionDTO;
import EventManagement.Event.DTO.VisitorAnswerDTO;
import EventManagement.Event.entity.FeedbackAnswer;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.entity.Visitor;
import EventManagement.Event.entity.VisitorAnswer;
import EventManagement.Event.repository.FeedBackAwserRepository;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.repository.VisitorAnswerRepository;
import EventManagement.Event.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackQuestionService2 {

    @Autowired
    private FeedBackQuestionRepository feedbackQuestionRepository;

    @Autowired
    private FeedBackAwserRepository feedbackAnswerRepository;

    @Autowired
    private VisitorAnswerRepository visitorAnswerRepository;
    @Autowired
    private VisitorRepository visitorRepository;

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
    public VisitorAnswerDTO createVisitorAnswer(VisitorAnswerDTO visitorAnswerDTO) {
        VisitorAnswer visitorAnswer = new VisitorAnswer();
        visitorAnswer.setVisitorAnswerFeedback(visitorAnswerDTO.getVisitorAnswerFeedback());
        visitorAnswer.setVisitorCount(visitorAnswerDTO.getVisitorCount());

        Optional<Visitor> visitorOptional = visitorRepository.findById(visitorAnswerDTO.getVisitorId());
        if (!visitorOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy Visitor với ID: " + visitorAnswerDTO.getVisitorId());
        }
        visitorAnswer.setVisitor(visitorOptional.get());

        Optional<FeedbackAnswer> feedbackAnswerOptional = feedbackAnswerRepository.findById(visitorAnswerDTO.getFeedbackAnswerId());
        if (!feedbackAnswerOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy FeedbackAnswer với ID: " + visitorAnswerDTO.getFeedbackAnswerId());
        }
        visitorAnswer.setFeedbackAnswer(feedbackAnswerOptional.get());

        VisitorAnswer savedVisitorAnswer = visitorAnswerRepository.save(visitorAnswer);

        VisitorAnswerDTO responseDTO = new VisitorAnswerDTO();
        responseDTO.setId(savedVisitorAnswer.getId());
        responseDTO.setVisitorId(savedVisitorAnswer.getVisitor().getId());
        responseDTO.setFeedbackAnswerId(savedVisitorAnswer.getFeedbackAnswer().getFeedbackAnswerID());
        responseDTO.setVisitorAnswerFeedback(savedVisitorAnswer.getVisitorAnswerFeedback());
        responseDTO.setVisitorCount(savedVisitorAnswer.getVisitorCount());

        return responseDTO;
    }
}