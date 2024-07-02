package EventManagement.Event.service;

import EventManagement.Event.DTO.*;
import EventManagement.Event.entity.*;
import EventManagement.Event.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (optionalFeedback.isEmpty()) {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + feedbackID);
        }

        Feedback feedback = optionalFeedback.get();

        // Cập nhật các thông tin từ FeedbackDTO vào Feedback
        if (feedbackDTO.getTitle() != null) {
            feedback.setTitle(feedbackDTO.getTitle());
        }
        if (feedbackDTO.getModifiedAt() != null) {
            feedback.setModifiedAt(feedbackDTO.getModifiedAt());
        }
        if (feedbackDTO.getDeletedAt() != null) {
            feedback.setDeleteAt(feedbackDTO.getDeletedAt());
        }

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
    public Feedback createFeedback(FeedbackDTO feedbackDTO, int eventid) {
        try {
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

            // Set eventId
            Optional<Event> optionalEvent = eventRepository.findById(eventid);
            if (optionalEvent.isPresent()) {
                feedback.setEvent(optionalEvent.get());
            } else {
                throw new IllegalArgumentException("Event not found with id: " + eventid);
            }

            return feedbackRepository.save(feedback);
        } catch (IllegalArgumentException e) {
            // Handle specific exceptions like invalid stateID or event not found
            e.printStackTrace(); // Replace with proper logging
        } catch (Exception e) {
            // Handle general exceptions
            e.printStackTrace(); // Replace with proper logging
        }

// Return null or handle the case where feedback couldn't be saved
        return null;
    }







        @Transactional
    public void deleteFeedback(int feedbackID) {
        feedbackRepository.deleteByFeedbackID(feedbackID);
    }






    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


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
//    public List<Feedback> getAllFeedbackByAccountId(HttpServletRequest request) {
//        String accountid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return feedbackRepository.findByAccount_Id(Integer.parseInt(accountid));
//    }




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
    public List<Feedback> getFeedbacksByEventID(int eventID) {
        return feedbackRepository.findByEvent_Id(eventID);
    }

public List<FeedbackEventDTO> getFeedbacksByAccountID(int accountID) {
    return feedbackRepository.findFeedbacksWithEventNameAndStateByAccountID(accountID);
}








}






