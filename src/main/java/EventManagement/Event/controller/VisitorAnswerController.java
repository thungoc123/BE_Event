package EventManagement.Event.controller;

import EventManagement.Event.DTO.FeedbackAnswerDetailsDTO;
import EventManagement.Event.DTO.VisitorAnswerDTO;
import EventManagement.Event.entity.VisitorAnswer;
import EventManagement.Event.service.FeedbackAnswerService;
import EventManagement.Event.service.FeedbackQuestionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-visitor-answer")
public class VisitorAnswerController {

    @Autowired
    private FeedbackQuestionService2 visitorAnswerService;
    @Autowired
    private FeedbackAnswerService feedbackAnswerService;

    @PostMapping("/visitor-answer")
    public VisitorAnswerDTO createVisitorAnswer(@RequestBody VisitorAnswerDTO visitorAnswerDTO) {
        return visitorAnswerService.createVisitorAnswer(visitorAnswerDTO);
    }
    @GetMapping("/feedback-answer-details")
    public List<FeedbackAnswerDetailsDTO> getAllFeedbackAnswerDetails() {
        return feedbackAnswerService.getAllFeedbackAnswerDetails();
    }
}
