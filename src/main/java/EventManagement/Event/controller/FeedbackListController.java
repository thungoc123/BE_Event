package EventManagement.Event.controller;

import EventManagement.Event.entity.Feedback;
import EventManagement.Event.repository.FeedbackRepository;
import EventManagement.Event.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api-feedback-list-event")
public class FeedbackListController {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/list-feedback/{eventID}")
    public List<Feedback> getFeedbacksByEventID(@PathVariable int eventID) {
        return feedbackService.getFeedbacksByEventID(eventID);
    }
}
