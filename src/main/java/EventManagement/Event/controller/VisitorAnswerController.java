package EventManagement.Event.controller;

import EventManagement.Event.DTO.CreateVisitorAnswerDTO;
import EventManagement.Event.DTO.FeedbackAnswerDetailsDTO;
import EventManagement.Event.DTO.VisitorAnswerDTO;
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



    }
