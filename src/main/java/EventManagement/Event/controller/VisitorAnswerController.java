package EventManagement.Event.controller;

import EventManagement.Event.DTO.*;
import EventManagement.Event.entity.VisitorAnswer;

import EventManagement.Event.payload.Request.CreateVisitorAnswerRequest;
import EventManagement.Event.service.VisitorAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-visitor-answer")
public class VisitorAnswerController {

    private final VisitorAnswerService visitorAnswerService;

    @Autowired
    public VisitorAnswerController(VisitorAnswerService visitorAnswerService) {
        this.visitorAnswerService = visitorAnswerService;
    }

    @PostMapping("/create")
    public ResponseEntity<VisitorAnswerDTO> createVisitorAnswer(@RequestBody CreateVisitorAnswerRequest request) {
        VisitorAnswerDTO visitorAnswerDTO = visitorAnswerService.createVisitorAnswer(
                request.getVisitorId(),
                request.getFeedbackQuestionId(),
                request.getVisitorAnswerFeedback()
        );
        return ResponseEntity.ok(visitorAnswerDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VisitorAnswerDTO> getVisitorAnswer(@PathVariable int id) {
        VisitorAnswerDTO visitorAnswerDTO = visitorAnswerService.getVisitorAnswer(id);
        return ResponseEntity.ok(visitorAnswerDTO);
    }
    @GetMapping("/all")
    public ResponseEntity<List<VisitorAnswerDTO>> getListVisitorAnswer() {
        List<VisitorAnswerDTO> visitorAnswers = visitorAnswerService.getListVisitorAnswer();
        return ResponseEntity.ok(visitorAnswers);
    }

    @GetMapping("/by-feedback/{feedbackId}")
    public ResponseEntity<List<VisitorFeedbackDTO>> getVisitorsByFeedbackId(@PathVariable int feedbackId) {
        List<VisitorFeedbackDTO> visitorFeedbackDTOs = visitorAnswerService.getVisitorsByFeedbackId(feedbackId);
        return ResponseEntity.ok(visitorFeedbackDTOs);
    }
    @GetMapping("/by-feedback/{feedbackId}/visitor/{visitorId}")
    public ResponseEntity<List<FeedbackVisitorDTO>> getVisitorAnswersByFeedbackIdAndVisitorId(@PathVariable int feedbackId, @PathVariable int visitorId) {
        List<FeedbackVisitorDTO> visitorAnswerDTOs = visitorAnswerService.getVisitorAnswersByFeedbackIdAndVisitorId(feedbackId, visitorId);
        return ResponseEntity.ok(visitorAnswerDTOs);
    }


    }
