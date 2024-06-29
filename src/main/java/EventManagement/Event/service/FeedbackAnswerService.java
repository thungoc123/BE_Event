package EventManagement.Event.service;

import EventManagement.Event.DTO.FeedbackAnswerDTO;
import EventManagement.Event.DTO.FeedbackAnswerDetailsDTO;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackAnswerService {

    @Autowired
    private FeedBackAwserRepository feedbackAnswerRepository;
    @Autowired
    private FeedBackQuestionRepository feedBackQuestionRepository;
    @Autowired
    private VisitorAnswerRepository visitorAnswerRepository;


    public List<FeedbackAnswerDetailsDTO> getAllFeedbackAnswerDetails() {
        List<FeedbackAnswer> feedbackAnswers = feedbackAnswerRepository.findAll();

        return feedbackAnswers.stream().map(feedbackAnswer -> {
            FeedbackAnswerDetailsDTO dto = new FeedbackAnswerDetailsDTO();
            dto.setAnswerId(feedbackAnswer.getFeedbackAnswerID());
            dto.setAnswer(feedbackAnswer.getAnswer());
            dto.setFeedbackQuestionId(feedbackAnswer.getFeedbackQuestion().getFeedbackQuestionID());
            dto.setTextQuestion(feedbackAnswer.getFeedbackQuestion().getTextQuestion());
            dto.setVisitorCount(visitorAnswerRepository.countByFeedbackAnswer(feedbackAnswer));
            return dto;
        }).collect(Collectors.toList());
    }


    public FeedbackAnswerDTO createFeedbackAnswer(FeedbackAnswerDTO feedbackAnswerDTO) {
        FeedbackAnswer feedbackAnswer = new FeedbackAnswer();
        feedbackAnswer.setDeletedAt(feedbackAnswerDTO.getDeletedAt());
        feedbackAnswer.setAnswer(feedbackAnswerDTO.getAnswer());
        feedbackAnswer.setModifiedAt(feedbackAnswerDTO.getModifiedAt());

        Optional<FeedbackQuestion> feedbackQuestionOptional = feedBackQuestionRepository.findById(feedbackAnswerDTO.getQuestion_id());
        if (!feedbackQuestionOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy FeedbackQuestion với ID: " + feedbackAnswerDTO.getQuestion_id());
        }

        feedbackAnswer.setFeedbackQuestion(feedbackQuestionOptional.get());

        FeedbackAnswer savedFeedbackAnswer = feedbackAnswerRepository.save(feedbackAnswer);

        FeedbackAnswerDTO responseDTO = new FeedbackAnswerDTO();
        responseDTO.setFeedbackAnswerID(savedFeedbackAnswer.getFeedbackAnswerID());
        responseDTO.setAnswer(savedFeedbackAnswer.getAnswer());
        responseDTO.setDeletedAt(savedFeedbackAnswer.getDeletedAt());
        responseDTO.setModifiedAt(savedFeedbackAnswer.getModifiedAt());
        responseDTO.setQuestion_id(savedFeedbackAnswer.getFeedbackQuestion().getFeedbackQuestionID()); // Sử dụng ID của câu hỏi

        return responseDTO;
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
    public List<FeedbackAnswerDTO> getListFeedbackAnswersByQuestionID(int feedbackQuestionID) {
        Optional<FeedbackQuestion> feedbackQuestionOptional = feedBackQuestionRepository.findById(feedbackQuestionID);
        if (!feedbackQuestionOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy FeedbackQuestion với ID: " + feedbackQuestionID);
        }

        FeedbackQuestion feedbackQuestion = feedbackQuestionOptional.get();
        List<FeedbackAnswer> feedbackAnswers = feedbackAnswerRepository.findByFeedbackQuestion(feedbackQuestion);

        return feedbackAnswers.stream().map(answer -> {
            FeedbackAnswerDTO dto = new FeedbackAnswerDTO();
            dto.setFeedbackAnswerID(answer.getFeedbackAnswerID());
            dto.setAnswer(answer.getAnswer());
            dto.setDeletedAt(answer.getDeletedAt());
            dto.setModifiedAt(answer.getModifiedAt());
            dto.setQuestion_id(feedbackQuestionID);
            return dto;
        }).collect(Collectors.toList());
    }
}


