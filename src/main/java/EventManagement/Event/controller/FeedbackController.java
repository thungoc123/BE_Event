package EventManagement.Event.controller;

import EventManagement.Event.DTO.FeedbackDTO;
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

//    @GetMapping("/getall")
//    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
//        List<FeedbackDTO> feedbackList = feedbackService.getAll();
//        return ResponseEntity.ok(feedbackList);
//    }

    @PostMapping("/create")
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        Feedback createdFeedback = feedbackService.createFeedback(feedbackDTO);
        return ResponseEntity.ok(createdFeedback);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable int id, @RequestBody FeedbackDTO feedbackDTO) {
        try {
            Feedback updatedFeedback = feedbackService.updateFeedback(id, feedbackDTO);
            return new ResponseEntity<>(updatedFeedback, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{feedbackID}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable int feedbackID) {
        feedbackService.deleteFeedback(feedbackID);
        return ResponseEntity.noContent().build();
    }



}


