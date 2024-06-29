package EventManagement.Event.controller;

import EventManagement.Event.DTO.FeedbackQuestionDTO;
import EventManagement.Event.DTO.FeedbackVisitorQuestionDTO;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.service.FeedbackQuestionService;
import EventManagement.Event.service.FeedbackQuestionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbackQuestions")
public class FeedbackQuestionController {

    @Autowired
    private FeedbackQuestionService2 feedbackQuestionService2;
    @Autowired
    private FeedbackQuestionService feedbackQuestionService;


    @PostMapping("/create")
    public FeedbackQuestionDTO createFeedbackQuestion(@RequestBody FeedbackQuestionDTO feedbackQuestionDTO) {
        return feedbackQuestionService.createFeedbackQuestion(feedbackQuestionDTO);
    }



    @GetMapping("/all")
    public ResponseEntity<List<FeedbackVisitorQuestionDTO>> getAllFeedbackQuestions() {
        List<FeedbackVisitorQuestionDTO> feedbackQuestions = feedbackQuestionService2.getAll();
        return new ResponseEntity<>(feedbackQuestions, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FeedbackQuestionDTO> updateFeedbackQuestion(
            @PathVariable int id,
            @RequestBody FeedbackQuestionDTO questionDTO) {
        try {
            FeedbackQuestion updatedQuestion = feedbackQuestionService.updateFeedbackQuestion(id, questionDTO);
            FeedbackQuestionDTO responseDTO = new FeedbackQuestionDTO();
            responseDTO.setTypeQuestion(updatedQuestion.getTypeQuestion());
            responseDTO.setTextQuestion(updatedQuestion.getTextQuestion());
            responseDTO.setDeletedAt(updatedQuestion.getDeletedAt());
            responseDTO.setModifiedAt(updatedQuestion.getModifiedAt());
            responseDTO.setFeedbackID(updatedQuestion.getFeedback().getFeedbackID());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{feedbackQuestionID}")
    public ResponseEntity<Void> deleteFeedbackQuestion(@PathVariable int feedbackQuestionID) {
        try {
            feedbackQuestionService.deleteFeedbackQuestion(feedbackQuestionID);
            return ResponseEntity.noContent().build();  // HTTP 204 No Content nếu xóa thành công
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // HTTP 404 Not Found nếu không tìm thấy FeedbackQuestion
        }
    }
    @GetMapping("/feedback/{feedbackID}")
    public ResponseEntity<List<FeedbackQuestionDTO>> getFeedbackQuestionsByFeedbackID(@PathVariable int feedbackID) {
        List<FeedbackQuestionDTO> feedbackQuestions = feedbackQuestionService.getListFeedbackQuestionsByFeedbackID(feedbackID);
        return ResponseEntity.ok(feedbackQuestions);
    }
}
