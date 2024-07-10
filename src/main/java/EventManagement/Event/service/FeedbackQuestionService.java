package EventManagement.Event.service;


import EventManagement.Event.DTO.EventDTO;
import EventManagement.Event.DTO.FeedbackQuestionDTO;
import EventManagement.Event.DTO.FeedbackQuestionEventDTO;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Feedback;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private EventRepository eventRepository;



    public FeedbackQuestionDTO createFeedbackQuestion(FeedbackQuestionDTO questionDTO, int feedbackid) {
        FeedbackQuestion feedbackQuestion = new FeedbackQuestion();
        feedbackQuestion.setTextQuestion(questionDTO.getTextQuestion());
        feedbackQuestion.setDeletedAt(questionDTO.getDeletedAt());
        feedbackQuestion.setModifiedAt(questionDTO.getModifiedAt());

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackid);
        if (!feedbackOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + feedbackid);
        }

        feedbackQuestion.setFeedback(feedbackOptional.get());

        FeedbackQuestion savedFeedbackQuestion = feedbackQuestionRepository.save(feedbackQuestion);

        FeedbackQuestionDTO responseDTO = new FeedbackQuestionDTO();
        responseDTO.setFeedbackQuestionID(savedFeedbackQuestion.getFeedbackQuestionID());
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


        feedbackQuestionRepository.delete(feedbackQuestion);
    }

    @Transactional
    public List<FeedbackQuestionDTO> getListFeedbackQuestionsByFeedbackID(int feedbackID) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackID);
        if (!feedbackOptional.isPresent()) {
            throw new RuntimeException("Không tìm thấy Feedback với ID: " + feedbackID);
        }

        Feedback feedback = feedbackOptional.get();
        List<FeedbackQuestion> feedbackQuestions = feedbackQuestionRepository.findByFeedback(feedback);

        // Lấy thông tin về Event từ Feedback
        Event event = feedback.getEvent(); // Event mà Feedback đang liên kết đến

        // Tạo list để lưu các DTO
        List<FeedbackQuestionDTO> dtos = feedbackQuestions.stream().map(fq -> {
            FeedbackQuestionDTO dto = new FeedbackQuestionDTO();
            dto.setFeedbackQuestionID(fq.getFeedbackQuestionID());
            dto.setTextQuestion(fq.getTextQuestion());
            dto.setDeletedAt(fq.getDeletedAt());
            dto.setModifiedAt(fq.getModifiedAt());
            dto.setFeedbackID(feedbackID);

            // Gán thông tin từ Event vào EventDTO của FeedbackQuestionDTO
            if (event != null) {
                EventDTO eventDTO = new EventDTO();
                eventDTO.setId(event.getId());
                eventDTO.setName(event.getName());
                eventDTO.setDescription(event.getDescription());
                eventDTO.setTimeclosesale(event.getTimeclosesale());
                eventDTO.setTimeend(event.getTimeend());
                eventDTO.setTimeopensale(event.getTimeopensale());
                eventDTO.setTimestart(event.getTimestart());

                dto.setEvent(eventDTO);
            }


            return dto;
        }).collect(Collectors.toList());

        return dtos;
    }
    public List<FeedbackQuestion> getFeedbackQuestionsByFeedbackID(int feedbackID) {
        return feedbackQuestionRepository.findByFeedback_FeedbackID(feedbackID);
    }
}
//    public List<FeedbackQuestionDTO> getListFeedbackQuestionsByFeedbackID2(int feedbackID) {
//        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackID);
//        if (!feedbackOptional.isPresent()) {
//            throw new RuntimeException("Không tìm thấy Feedback với ID: " + feedbackID);
//        }
//
//        Feedback feedback = feedbackOptional.get();
//        List<FeedbackQuestion> feedbackQuestions = feedbackQuestionRepository.findByFeedback(feedback);
//
//        // Lấy danh sách các Event tương ứng với Feedback
//        List<Event> events = eventRepository.findByFeedback(feedback);
//
//        return feedbackQuestions.stream().map(fq -> {
//            FeedbackQuestionDTO dto = new FeedbackQuestionDTO();
//            dto.setFeedbackQuestionID(fq.getFeedbackQuestionID());
//            dto.setTextQuestion(fq.getTextQuestion());
//            dto.setDeletedAt(fq.getDeletedAt());
//            dto.setModifiedAt(fq.getModifiedAt());
//            dto.setFeedbackID(feedbackID);
//
//            // Tìm và thiết lập thông tin Event tương ứng
//            Optional<Event> correspondingEvent = events.stream()
//                    .filter(event -> event.getId() == fq.getFeedback().getEvent().getId())
//                    .findFirst();
//            correspondingEvent.ifPresent(event -> {
//                dto.setEventID(event.getId());
//                dto.setEventName(event.getName());
//                // Thiết lập các thông tin khác của Event cần thiết
//            });
//
//            return dto;
//        }).collect(Collectors.toList());
//    }






