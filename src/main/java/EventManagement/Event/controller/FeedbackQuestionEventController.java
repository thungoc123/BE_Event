package EventManagement.Event.controller;


import EventManagement.Event.DTO.FeedbackQuestionDTO;
import EventManagement.Event.DTO.FeedbackQuestionEventDTO;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.repository.FeedBackQuestionRepository;
import EventManagement.Event.service.FeedbackQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api-feedback-questions-event")
public class FeedbackQuestionEventController {

    @Autowired
    private FeedbackQuestionService feedbackQuestionService;

    @Autowired
    private FeedBackQuestionRepository feedBackQuestionRepository;

    @GetMapping("/feedback/{feedbackID}")
    public ResponseEntity<List<FeedbackQuestionDTO>> getFeedbackQuestionsByFeedbackID(@PathVariable int feedbackID) {
        List<FeedbackQuestionDTO> feedbackQuestions = feedbackQuestionService.getListFeedbackQuestionsByFeedbackID(feedbackID);
        return ResponseEntity.ok(feedbackQuestions);
    }

    @GetMapping("feedback-question/feedback/{feedbackID}")
    public List<FeedbackQuestion> getFeedbackQuestionsByFeedbackID2(@PathVariable int feedbackID) {
        return feedbackQuestionService.getFeedbackQuestionsByFeedbackID(feedbackID);
    }
}
