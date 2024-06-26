package EventManagement.Event.controller;

import EventManagement.Event.DTO.FeedbackAnswerDTO;
import EventManagement.Event.DTO.FeedbackAnswerDetailsDTO;
import EventManagement.Event.entity.FeedbackAnswer;
import EventManagement.Event.service.FeedbackAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-feedbackanswer")
public class FeedbackAnswerController {




    @Autowired
    private FeedbackAnswerService feedbackAnswerService;



    @GetMapping("/feedback-answer-details")
    public List<FeedbackAnswerDetailsDTO> getAllFeedbackAnswerDetails() {
        return feedbackAnswerService.getAllFeedbackAnswerDetails();
    }



    @PostMapping("/create")
    public FeedbackAnswerDTO createFeedbackAnswer(@RequestBody FeedbackAnswerDTO feedbackAnswerDTO) {
        return feedbackAnswerService.createFeedbackAnswer(feedbackAnswerDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FeedbackAnswerDTO> updateFeedbackAnswer(
            @PathVariable int id,
            @RequestBody FeedbackAnswerDTO feedbackAnswerDTO) {
        try {
            FeedbackAnswer updatedAnswer = feedbackAnswerService.updateFeedbackAnswer(id, feedbackAnswerDTO);
            FeedbackAnswerDTO responseDTO = new FeedbackAnswerDTO();
            responseDTO.setAnswer(updatedAnswer.getAnswer());
            responseDTO.setDeletedAt(updatedAnswer.getDeletedAt());
            responseDTO.setModifiedAt(updatedAnswer.getModifiedAt());
            responseDTO.setQuestion_id(updatedAnswer.getFeedbackQuestion().getFeedbackQuestionID());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{feedbackAnswerID}")
    public ResponseEntity<Void> deleteFeedbackAnswer(@PathVariable int feedbackAnswerID) {
        try {
            feedbackAnswerService.deleteFeedbackAnswer(feedbackAnswerID);
            return ResponseEntity.noContent().build();  // HTTP 204 No Content nếu xóa thành công
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // HTTP 404 Not Found nếu không tìm thấy FeedbackAnswer
        }
    }

}
