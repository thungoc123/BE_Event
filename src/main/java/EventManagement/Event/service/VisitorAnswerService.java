package EventManagement.Event.service;

import EventManagement.Event.DTO.*;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.entity.Visitor;
import EventManagement.Event.entity.VisitorAnswer;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.repository.VisitorAnswerRepository;
import EventManagement.Event.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Đánh dấu đây là một service để Spring có thể quản lý và inject
public class VisitorAnswerService {

    private final VisitorAnswerRepository visitorAnswerRepository;
    private final FeedBackQuestionRepository feedbackQuestionRepository;
    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorAnswerService(VisitorAnswerRepository visitorAnswerRepository,
                                FeedBackQuestionRepository feedbackQuestionRepository,
                                VisitorRepository visitorRepository) {
        this.visitorAnswerRepository = visitorAnswerRepository;
        this.feedbackQuestionRepository = feedbackQuestionRepository;
        this.visitorRepository = visitorRepository;
    }

    public VisitorAnswerDTO createVisitorAnswer(int visitorId, int feedbackQuestionId, String visitorAnswerFeedback) {
        // Kiểm tra xem Visitor và FeedbackQuestion có tồn tại không
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Visitor với ID: " + visitorId));

        FeedbackQuestion feedbackQuestion = feedbackQuestionRepository.findById(feedbackQuestionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy FeedbackQuestion với ID: " + feedbackQuestionId));

        // Tạo một đối tượng VisitorAnswer mới
        VisitorAnswer visitorAnswer = new VisitorAnswer();
        visitorAnswer.setVisitor(visitor);
        visitorAnswer.setFeedbackQuestion(feedbackQuestion);
        visitorAnswer.setVisitorAnswerFeedback(visitorAnswerFeedback);

        // Lưu VisitorAnswer vào cơ sở dữ liệu
        VisitorAnswer savedVisitorAnswer = visitorAnswerRepository.save(visitorAnswer);

        // Chuyển đổi VisitorAnswer sang VisitorAnswerDTO
        VisitorAnswerDTO visitorAnswerDTO = new VisitorAnswerDTO();
        visitorAnswerDTO.setId(savedVisitorAnswer.getId());
        visitorAnswerDTO.setVisitorId(savedVisitorAnswer.getVisitor().getId());
        visitorAnswerDTO.setFeedbackQuestionId(savedVisitorAnswer.getFeedbackQuestion().getFeedbackQuestionID());
        visitorAnswerDTO.setVisitorAnswerFeedback(savedVisitorAnswer.getVisitorAnswerFeedback());


        return visitorAnswerDTO;
    }
    public VisitorAnswerDTO getVisitorAnswer(int id) {
        VisitorAnswer visitorAnswer = visitorAnswerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy VisitorAnswer với ID: " + id));

        // Chuyển đổi VisitorAnswer sang VisitorAnswerDTO
        VisitorAnswerDTO visitorAnswerDTO = new VisitorAnswerDTO();
        visitorAnswerDTO.setId(visitorAnswer.getId());
        visitorAnswerDTO.setVisitorId(visitorAnswer.getVisitor().getId());
        visitorAnswerDTO.setFeedbackQuestionId(visitorAnswer.getFeedbackQuestion().getFeedbackQuestionID());
        visitorAnswerDTO.setVisitorAnswerFeedback(visitorAnswer.getVisitorAnswerFeedback());

        return visitorAnswerDTO;
    }
    public List<VisitorAnswerDTO> getListVisitorAnswer() {
        List<VisitorAnswer> visitorAnswers = visitorAnswerRepository.findAll();
        return visitorAnswers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private VisitorAnswerDTO convertToDTO(VisitorAnswer visitorAnswer) {
        VisitorAnswerDTO visitorAnswerDTO = new VisitorAnswerDTO();
        visitorAnswerDTO.setId(visitorAnswer.getId());
        visitorAnswerDTO.setVisitorId(visitorAnswer.getVisitor().getId());
        visitorAnswerDTO.setFeedbackQuestionId(visitorAnswer.getFeedbackQuestion().getFeedbackQuestionID());
        visitorAnswerDTO.setVisitorAnswerFeedback(visitorAnswer.getVisitorAnswerFeedback());

        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setId(visitorAnswer.getVisitor().getId());
        visitorDTO.setInformation(visitorAnswer.getVisitor().getInformation());
        visitorDTO.setAccount_id(visitorAnswer.getVisitor().getAccount().getId());
        visitorAnswerDTO.setVisitorDTO(visitorDTO);

        FeedbackQuestionDTO feedbackQuestionDTO = new FeedbackQuestionDTO();
        feedbackQuestionDTO.setFeedbackQuestionID(visitorAnswer.getFeedbackQuestion().getFeedbackQuestionID());
        feedbackQuestionDTO.setTextQuestion(visitorAnswer.getFeedbackQuestion().getTextQuestion());
        feedbackQuestionDTO.setDeletedAt(visitorAnswer.getFeedbackQuestion().getDeletedAt());
        feedbackQuestionDTO.setModifiedAt(visitorAnswer.getFeedbackQuestion().getModifiedAt());
        feedbackQuestionDTO.setFeedbackID(visitorAnswer.getFeedbackQuestion().getFeedback().getFeedbackID());

        EventDTO eventDTO = new EventDTO();
        Event event = visitorAnswer.getFeedbackQuestion().getFeedback().getEvent();
        if (event != null) {
            eventDTO.setId(event.getId());
            eventDTO.setName(event.getName());
            eventDTO.setDescription(event.getDescription());
            eventDTO.setTimeclosesale(event.getTimeclosesale());
            eventDTO.setTimeend(event.getTimeend());
            eventDTO.setTimeopensale(event.getTimeopensale());
            eventDTO.setTimestart(event.getTimestart());
        }
        feedbackQuestionDTO.setEvent(eventDTO);

        visitorAnswerDTO.setFeedbackQuestionDTO(feedbackQuestionDTO);

        return visitorAnswerDTO;
    }

    public List<VisitorFeedbackDTO> getVisitorsByFeedbackId(int feedbackId) {
        List<VisitorAnswer> visitorAnswers = visitorAnswerRepository.findByFeedbackQuestion_Feedback_FeedbackID(feedbackId);
        return visitorAnswers.stream().map(this::convertToVisitorFeedbackDTO).collect(Collectors.toList());
    }

    private VisitorFeedbackDTO convertToVisitorFeedbackDTO(VisitorAnswer visitorAnswer) {
        VisitorFeedbackDTO dto = new VisitorFeedbackDTO();
        dto.setVisitorId(visitorAnswer.getVisitor().getId());
        dto.setFeedbackId(visitorAnswer.getFeedbackQuestion().getFeedback().getFeedbackID());
        dto.setEmail(visitorAnswer.getVisitor().getAccount().getEmail());
        return dto;
    }

    public List<FeedbackVisitorDTO> getVisitorAnswersByFeedbackIdAndVisitorId(int feedbackId, int visitorId) {
        List<VisitorAnswer> visitorAnswers = visitorAnswerRepository.findByFeedbackQuestion_Feedback_FeedbackIDAndVisitor_Id(feedbackId, visitorId);
        return visitorAnswers.stream().map(this::convertToFeedbackVisitorDTO).collect(Collectors.toList());
    }

    private FeedbackVisitorDTO convertToFeedbackVisitorDTO(VisitorAnswer visitorAnswer) {
        FeedbackVisitorDTO dto = new FeedbackVisitorDTO();
        dto.setQuestionId(visitorAnswer.getFeedbackQuestion().getFeedbackQuestionID());
        dto.setQuestionText(visitorAnswer.getFeedbackQuestion().getTextQuestion());
        dto.setAnswerId(visitorAnswer.getId());
        dto.setAnswerText(visitorAnswer.getVisitorAnswerFeedback());
        return dto;
    }
}


