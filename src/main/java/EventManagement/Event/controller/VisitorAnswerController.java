package EventManagement.Event.controller;

import EventManagement.Event.DTO.VisitorAnswerDTO;
import EventManagement.Event.entity.VisitorAnswer;
import EventManagement.Event.service.FeedbackQuestionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-visitor-answer")
public class VisitorAnswerController {

    @Autowired
    private FeedbackQuestionService2 visitorAnswerService;

    @PostMapping("/visitor-answer")
    public VisitorAnswerDTO createVisitorAnswer(@RequestBody VisitorAnswerDTO visitorAnswerDTO) {
        return visitorAnswerService.createVisitorAnswer(visitorAnswerDTO);
    }
}
