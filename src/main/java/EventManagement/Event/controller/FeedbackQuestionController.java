package EventManagement.Event.controller;

import EventManagement.Event.DTO.FeedbackQuestionDTO;
import EventManagement.Event.DTO.FeedbackQuestionEventDTO;
import EventManagement.Event.DTO.FeedbackVisitorQuestionDTO;
import EventManagement.Event.entity.FeedbackQuestion;
import EventManagement.Event.payload.DeleteResponse;
import EventManagement.Event.service.FeedbackQuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbackQuestions")
public class FeedbackQuestionController {

//    @Autowired
//    private FeedbackQuestionService2 feedbackQuestionService2;
    @Autowired
    private FeedbackQuestionService feedbackQuestionService;


    @PostMapping("/questions/{feedbackId}")
    public ResponseEntity<FeedbackQuestionDTO> createFeedbackQuestion(@PathVariable int feedbackId,
                                                                      @RequestBody FeedbackQuestionDTO questionDTO) {
        try {
            FeedbackQuestionDTO createdQuestion = feedbackQuestionService.createFeedbackQuestion(questionDTO, feedbackId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



//    @GetMapping("/all")
//    public ResponseEntity<List<FeedbackVisitorQuestionDTO>> getAllFeedbackQuestions() {
//        List<FeedbackVisitorQuestionDTO> feedbackQuestions = feedbackQuestionService2.getAll();
//        return new ResponseEntity<>(feedbackQuestions, HttpStatus.OK);
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FeedbackQuestionDTO> updateFeedbackQuestion(
            @PathVariable int id,
            @RequestBody FeedbackQuestionDTO questionDTO) {
        try {
            FeedbackQuestion updatedQuestion = feedbackQuestionService.updateFeedbackQuestion(id, questionDTO);
            FeedbackQuestionDTO responseDTO = new FeedbackQuestionDTO();
//            responseDTO.setTypeQuestion(updatedQuestion.getTypeQuestion());
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
    public ResponseEntity<Object> deleteFeedbackQuestion(@PathVariable int feedbackQuestionID) {
        try {
            feedbackQuestionService.deleteFeedbackQuestion(feedbackQuestionID);
            return ResponseEntity.ok(new DeleteResponse("delete feedbackquestion successfully"));  // HTTP 204 No Content nếu xóa thành công
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // HTTP 404 Not Found nếu không tìm thấy FeedbackQuestion
        }
    }
//    @GetMapping("/feedback/{feedbackID}")
//    public List<FeedbackQuestionDTO> getFeedbackQuestionsByFeedbackID(@PathVariable int feedbackID) {
//        return feedbackQuestionService.getListFeedbackQuestionsByFeedbackID(feedbackID);
//    }



}
