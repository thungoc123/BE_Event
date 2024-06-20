package EventManagement.Event.service;

import EventManagement.Event.DTO.FeedbackAnswerDTO;
import EventManagement.Event.DTO.FeedbackQuestionDTO;
import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.FeedbackAnswer;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.repository.FeedBackAwserRepository;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.repository.VisitorAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FeedbackAnswerService {

    @Autowired
    private FeedBackAwserRepository feedbackAnswerRepository;
    @Autowired
    private FeedBackQuestionRepository feedBackQuestionRepository;
    @Autowired
    private VisitorAnswerRepository visitorAnswerRepository;



    public void createFeedbackAnswer(FeedbackAnswerDTO feedbackAnswerDTO) {
        FeedbackAnswer feedbackAnswer = new FeedbackAnswer();
        feedbackAnswer.setDeletedAt(feedbackAnswerDTO.getDeletedAt());
        feedbackAnswer.setAnswer(feedbackAnswerDTO.getAnswer());
        feedbackAnswer.setModifiedAt(feedbackAnswerDTO.getModifiedAt());

        Optional<FeedbackQuestion> feedbackOptional = feedBackQuestionRepository.findById(feedbackAnswerDTO.getQuestion_id());
        if (!feedbackOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + feedbackAnswerDTO.getQuestion_id());
        }

        feedbackAnswer.setFeedbackQuestion(feedbackOptional.get());

        feedbackAnswerRepository.save(feedbackAnswer);
    }
    public FeedbackAnswer updateFeedbackAnswer(int feedbackAnswerID, FeedbackAnswerDTO feedbackAnswerDTO) {
        Optional<FeedbackAnswer> feedbackAnswerOptional = feedbackAnswerRepository.findById(feedbackAnswerID);
        if (!feedbackAnswerOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy FeedbackAnswer với ID: " + feedbackAnswerID);
        }

        FeedbackAnswer feedbackAnswer = feedbackAnswerOptional.get();
        feedbackAnswer.setAnswer(feedbackAnswerDTO.getAnswer());
        feedbackAnswer.setDeletedAt(feedbackAnswerDTO.getDeletedAt());
        feedbackAnswer.setModifiedAt(feedbackAnswerDTO.getModifiedAt());

        Optional<FeedbackQuestion> feedbackQuestionOptional = feedBackQuestionRepository.findById(feedbackAnswerDTO.getQuestion_id());
        if (!feedbackQuestionOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy FeedbackQuestion với ID: " + feedbackAnswerDTO.getQuestion_id());
        }

        feedbackAnswer.setFeedbackQuestion(feedbackQuestionOptional.get());

        return feedbackAnswerRepository.save(feedbackAnswer);
    }
    public void deleteFeedbackAnswer(int feedbackAnswerID) {
        Optional<FeedbackAnswer> feedbackAnswerOptional = feedbackAnswerRepository.findById(feedbackAnswerID);
        if (!feedbackAnswerOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy FeedbackAnswer với ID: " + feedbackAnswerID);
        }

        FeedbackAnswer feedbackAnswer = feedbackAnswerOptional.get();

        // Xóa các bản ghi liên quan trong bảng VisitorAnswer trước
        visitorAnswerRepository.deleteByFeedbackAnswer(feedbackAnswer);

        // Xóa bản ghi FeedbackAnswer
        feedbackAnswerRepository.delete(feedbackAnswer);
    }
}


