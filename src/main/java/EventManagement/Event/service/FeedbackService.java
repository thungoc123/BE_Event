package EventManagement.Event.service;

import EventManagement.Event.DTO.FeedbackAnswerDTO;
import EventManagement.Event.DTO.FeedbackDTO;
import EventManagement.Event.DTO.FeedbackDataDTO;
import EventManagement.Event.DTO.FeedbackQuestionDTO;
import EventManagement.Event.entity.*;
import EventManagement.Event.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public Feedback updateFeedback(int feedbackID, FeedbackDTO feedbackDTO) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackID);

        if (!optionalFeedback.isPresent()) {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + feedbackID);
        }

        Feedback feedback = optionalFeedback.get();

        // Cập nhật các thông tin từ FeedbackDTO vào Feedback
        feedback.setTitle(feedbackDTO.getTitle());
        feedback.setModifiedAt(feedbackDTO.getModifiedAt());
        feedback.setDeleteAt(feedbackDTO.getDeletedAt());

        // Cập nhật State nếu stateID được cung cấp và là một giá trị hợp lệ
        if (feedbackDTO.getStateID() > 0) {
            Optional<State> optionalState = stateRepository.findById(feedbackDTO.getStateID());
            if (optionalState.isPresent()) {
                feedback.setState(optionalState.get());
            } else {
                throw new IllegalArgumentException("Invalid stateID: " + feedbackDTO.getStateID());
            }
        } else {
            feedback.setState(null); // Đặt state là null nếu không cung cấp stateID hoặc stateID là 0
        }

        // Lưu feedback đã được cập nhật và trả về
        return feedbackRepository.save(feedback);
    }



    @Transactional
    public Feedback createFeedback(FeedbackDTO feedbackDTO, Long accountId) {
        Feedback feedback = new Feedback();
        feedback.setTitle(feedbackDTO.getTitle());
        feedback.setModifiedAt(feedbackDTO.getModifiedAt());
        feedback.setDeleteAt(feedbackDTO.getDeletedAt());

        // Set default stateId to 2
        int stateId = 2;
        Optional<State> optionalState = stateRepository.findById(stateId);
        if (optionalState.isPresent()) {
            feedback.setState(optionalState.get());
        } else {
            throw new IllegalArgumentException("Invalid predefined stateID: " + stateId);
        }

        // Set accountId
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            feedback.setAccount(optionalAccount.get());
        } else {
            throw new IllegalArgumentException("Account not found with id: " + accountId);
        }

        return feedbackRepository.save(feedback);
    }








    @Transactional
    public void deleteFeedback(int feedbackID) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackID);

        if (optionalFeedback.isPresent()) {
            feedbackRepository.delete(optionalFeedback.get());
        } else {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + feedbackID);
        }
    }



    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

//    public List<FeedbackDTO> getAll() {
//        List<Feedback> feedbacks = feedbackRepository.findAll();
//
//        // Initialize lazy-loaded collections
//        for (Feedback feedback : feedbacks) {
//            Set<FeedbackQuestion> questions = feedback.getFeedbackQuestions();
//            questions.forEach(question -> {
//                question.getFeedbackAnswers().size(); // Initialize answers
//            });
//        }
//
//        return feedbacks.stream().map(this::convertFeedbackToDTO).collect(Collectors.toList());
//    }
    public FeedbackDataDTO getAllFeedbackData() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<FeedbackQuestion> feedbackQuestions = feedBackQuestionRepository.findAll();
        List<FeedbackAnswer> feedbackAnswers = feedBackAwserRepository.findAll();

        FeedbackDataDTO feedbackDataDTO = new FeedbackDataDTO();
        feedbackDataDTO.setFeedbacks(feedbacks);
        feedbackDataDTO.setFeedbackQuestions(feedbackQuestions);
        feedbackDataDTO.setFeedbackAnswers(feedbackAnswers);

        return feedbackDataDTO;
    }
    public List<FeedbackDTO> getAllFeedbackByAccountId(Long accountId) {
        List<Feedback> feedbackList = feedbackRepository.findByAccount_Id(accountId);
        return feedbackList.stream().map(this::convertFeedbackToDTO).collect(Collectors.toList());
    }



    private FeedbackDTO convertFeedbackToDTO(Feedback feedback) {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackID(feedback.getFeedbackID());
        feedbackDTO.setTitle(feedback.getTitle());
        feedbackDTO.setDeletedAt(feedback.getDeleteAt());
        feedbackDTO.setModifiedAt(feedback.getModifiedAt());
        feedbackDTO.setStateID(feedback.getState().getStateId());

        List<FeedbackQuestionDTO> feedbackQuestionDTOs = feedback.getFeedbackQuestions().stream()
                .map(this::convertFeedbackQuestionToDTO)
                .collect(Collectors.toList());
        feedbackDTO.setFeedbackQuestions(feedbackQuestionDTOs);

        return feedbackDTO;
    }

    private FeedbackQuestionDTO convertFeedbackQuestionToDTO(FeedbackQuestion feedbackQuestion) {
        FeedbackQuestionDTO feedbackQuestionDTO = new FeedbackQuestionDTO();
        feedbackQuestionDTO.setFeedbackQuestionID(feedbackQuestion.getFeedbackQuestionID());
        feedbackQuestionDTO.setTypeQuestion(feedbackQuestion.getTypeQuestion());
        feedbackQuestionDTO.setTextQuestion(feedbackQuestion.getTextQuestion());
        feedbackQuestionDTO.setDeletedAt(feedbackQuestion.getDeletedAt());
        feedbackQuestionDTO.setModifiedAt(feedbackQuestion.getModifiedAt());

        List<FeedbackAnswerDTO> feedbackAnswerDTOs = feedbackQuestion.getFeedbackAnswers().stream()
                .map(this::convertFeedbackAnswerToDTO)
                .collect(Collectors.toList());
        feedbackQuestionDTO.setAnswers(feedbackAnswerDTOs);

        return feedbackQuestionDTO;
    }

    private FeedbackAnswerDTO convertFeedbackAnswerToDTO(FeedbackAnswer feedbackAnswer) {
        FeedbackAnswerDTO feedbackAnswerDTO = new FeedbackAnswerDTO();
        feedbackAnswerDTO.setFeedbackAnswerID(feedbackAnswer.getFeedbackAnswerID());
        feedbackAnswerDTO.setAnswer(feedbackAnswer.getAnswer());
        feedbackAnswerDTO.setDeletedAt(feedbackAnswer.getDeletedAt());
        feedbackAnswerDTO.setModifiedAt(feedbackAnswer.getModifiedAt());

        return feedbackAnswerDTO;
    }







}






