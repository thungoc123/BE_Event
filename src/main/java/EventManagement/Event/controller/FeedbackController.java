package EventManagement.Event.controller;

import EventManagement.Event.DTO.FeedbackDTO;
import EventManagement.Event.DTO.FeedbackDataDTO;
import EventManagement.Event.DTO.FeedbackEventDTO;
import EventManagement.Event.entity.Feedback;
import EventManagement.Event.payload.DeleteResponse;
import EventManagement.Event.repository.FeedbackRepository;
import EventManagement.Event.service.FeedbackService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api-feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllFeedBack() {

        return new ResponseEntity<>("FeedBack", HttpStatus.OK);
    }

//    @GetMapping("/getall")
//    public FeedbackDataDTO getAllFeedbackData() {
//        return feedbackService.getAllFeedbackData();
//    }


    @PostMapping("/events/{eventId}")
    public ResponseEntity<Feedback> createFeedback(@PathVariable Integer eventId,
                                                   @RequestBody FeedbackDTO feedbackDTO) {
        try {
            Feedback createdFeedback = feedbackService.createFeedback(feedbackDTO, eventId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
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
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }



//    @DeleteMapping("/delete/{feedbackID}")
//    public ResponseEntity<Object> deleteFeedback(@PathVariable int feedbackID) {
//        try {
//            feedbackService.deleteFeedback(feedbackID);
//            return ResponseEntity.ok(new DeleteResponse("delete feedback successfully"));
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }


//    @GetMapping("/account")
//    public List<Feedback> getAllFeedbackByAccountId(HttpServletRequest request) {
//        String accountid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return feedbackRepository.findByAccount_Id(Integer.parseInt(accountid));
//    }
    @GetMapping("/list-feedback/{eventID}")
    public List<Feedback> getFeedbacksByEventID(@PathVariable int eventID) {
    return feedbackService.getFeedbacksByEventID(eventID);
}
    @GetMapping("/list-feedback-account/{accountID}")
    public List<FeedbackEventDTO> getFeedbacksByAccountID(@PathVariable int accountID) {
        return feedbackService.getFeedbacksByAccountID(accountID);

    }
//    @DeleteMapping("/{feedbackId}")
//    public ResponseEntity<Void> deleteFeedbackById(@PathVariable int feedbackId) {
//        try {
//            feedbackService.deleteFeedbackById(feedbackId);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            // Log lỗi nếu cần
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable int feedbackId) {
        try {
            feedbackService.deleteFeedbackById(feedbackId); // Thay đổi tên phương thức gọi từ service
            return ResponseEntity.ok(new DeleteResponse("delete feedback successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete feedback with id: " + feedbackId);
        }
    }
    @PutMapping("/{feedbackID}/state/{stateID}")
    public ResponseEntity<Feedback> updateState(@PathVariable int feedbackID, @PathVariable int stateID) {
        Feedback updatedFeedback = feedbackService.updateState(feedbackID, stateID);
        return ResponseEntity.ok(updatedFeedback);
    }

}


