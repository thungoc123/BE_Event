package EventManagement.Event.service;

import EventManagement.Event.DTO.FeedbackQuestionDTO;
import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackQuestionService {

    @Autowired
    private FeedBackQuestionRepository feedbackQuestionRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FeedbackAnswerService feedbackAnswerService;

    public FeedbackQuestionDTO createFeedbackQuestion(FeedbackQuestionDTO questionDTO) {
        FeedbackQuestion feedbackQuestion = new FeedbackQuestion();
        feedbackQuestion.setTypeQuestion(questionDTO.getTypeQuestion());
        feedbackQuestion.setTextQuestion(questionDTO.getTextQuestion());
        feedbackQuestion.setDeletedAt(questionDTO.getDeletedAt());
        feedbackQuestion.setModifiedAt(questionDTO.getModifiedAt());

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(questionDTO.getFeedbackID());
        if (!feedbackOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + questionDTO.getFeedbackID());
        }

        feedbackQuestion.setFeedback(feedbackOptional.get());

        FeedbackQuestion savedFeedbackQuestion = feedbackQuestionRepository.save(feedbackQuestion);

        FeedbackQuestionDTO responseDTO = new FeedbackQuestionDTO();
        responseDTO.setFeedbackQuestionID(savedFeedbackQuestion.getFeedbackQuestionID());
        responseDTO.setTypeQuestion(savedFeedbackQuestion.getTypeQuestion());
        responseDTO.setTextQuestion(savedFeedbackQuestion.getTextQuestion());
        responseDTO.setDeletedAt(savedFeedbackQuestion.getDeletedAt());
        responseDTO.setModifiedAt(savedFeedbackQuestion.getModifiedAt());
        responseDTO.setFeedbackID(savedFeedbackQuestion.getFeedback().getFeedbackID());

        return responseDTO;
    }

    public FeedbackQuestion updateFeedbackQuestion(int feedbackQuestionID, FeedbackQuestionDTO questionDTO) {
        Optional<FeedbackQuestion> feedbackQuestionOptional = feedbackQuestionRepository.findById(feedbackQuestionID);
        if (!feedbackQuestionOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy FeedbackQuestion với ID: " + feedbackQuestionID);
        }

        FeedbackQuestion feedbackQuestion = feedbackQuestionOptional.get();
        feedbackQuestion.setTypeQuestion(questionDTO.getTypeQuestion());
        feedbackQuestion.setTextQuestion(questionDTO.getTextQuestion());
        feedbackQuestion.setDeletedAt(questionDTO.getDeletedAt());
        feedbackQuestion.setModifiedAt(questionDTO.getModifiedAt());

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(questionDTO.getFeedbackID());
        if (!feedbackOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + questionDTO.getFeedbackID());
        }

        feedbackQuestion.setFeedback(feedbackOptional.get());

        return feedbackQuestionRepository.save(feedbackQuestion);
    }
    public void deleteFeedbackQuestion(int feedbackQuestionID) {
        Optional<FeedbackQuestion> feedbackQuestionOptional = feedbackQuestionRepository.findById(feedbackQuestionID);
        if (!feedbackQuestionOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy FeedbackQuestion với ID: " + feedbackQuestionID);
        }

        FeedbackQuestion feedbackQuestion = feedbackQuestionOptional.get();

        // Xóa tất cả FeedbackAnswer liên quan
        feedbackQuestion.getFeedbackAnswers().forEach(answer -> feedbackAnswerService.deleteFeedbackAnswer(answer.getFeedbackAnswerID()));

        // Xóa FeedbackQuestion
        feedbackQuestionRepository.delete(feedbackQuestion);
    }
    public List<FeedbackQuestionDTO> getListFeedbackQuestionsByFeedbackID(int feedbackID) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackID);
        if (!feedbackOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + feedbackID);
        }

        Feedback feedback = feedbackOptional.get();
        List<FeedbackQuestion> feedbackQuestions = feedbackQuestionRepository.findByFeedback(feedback);

        return feedbackQuestions.stream().map(fq -> {
            FeedbackQuestionDTO dto = new FeedbackQuestionDTO();
            dto.setFeedbackQuestionID(fq.getFeedbackQuestionID());
            dto.setTypeQuestion(fq.getTypeQuestion());
            dto.setTextQuestion(fq.getTextQuestion());
            dto.setDeletedAt(fq.getDeletedAt());
            dto.setModifiedAt(fq.getModifiedAt());
            dto.setFeedbackID(feedbackID);
            return dto;
        }).collect(Collectors.toList());
    }
}

