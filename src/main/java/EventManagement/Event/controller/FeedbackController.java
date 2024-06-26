package EventManagement.Event.controller;

import EventManagement.Event.DTO.FeedbackDTO;
import EventManagement.Event.DTO.FeedbackDataDTO;
import EventManagement.Event.entity.Feedback;
import EventManagement.Event.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("")
    public ResponseEntity<?> getAllFeedBack() {

        return new ResponseEntity<>("FeedBack", HttpStatus.OK);
    }

    @GetMapping("/getall")
    public FeedbackDataDTO getAllFeedbackData() {
        return feedbackService.getAllFeedbackData();
    }


    @PostMapping("/create")
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        Feedback createdFeedback = feedbackService.createFeedback(feedbackDTO, feedbackDTO.getAccountId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable int id, @RequestBody FeedbackDTO feedbackDTO) {
        try {
            Feedback updatedFeedback = feedbackService.updateFeedback(id, feedbackDTO);
            if (updatedFeedback != null) {
                return ResponseEntity.ok(updatedFeedback);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/delete/{feedbackID}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable int feedbackID) {
        feedbackService.deleteFeedback(feedbackID);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/account/{accountId}")
    public List<FeedbackDTO> getAllFeedbackByAccountId(@PathVariable Long accountId) {
        return feedbackService.getAllFeedbackByAccountId(accountId);
    }




}


