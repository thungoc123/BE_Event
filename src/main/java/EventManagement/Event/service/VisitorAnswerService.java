package EventManagement.Event.service;

import EventManagement.Event.DTO.CreateVisitorAnswerDTO;
import EventManagement.Event.DTO.VisitorAnswerDTO;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.entity.Visitor;
import EventManagement.Event.entity.VisitorAnswer;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.repository.VisitorAnswerRepository;
import EventManagement.Event.repository.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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


}


