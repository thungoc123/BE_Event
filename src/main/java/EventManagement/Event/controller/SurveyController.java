package EventManagement.Event.controller;

import EventManagement.Event.DTO.FeedbackEventDTO;
import EventManagement.Event.DTO.SurveyDTO;
import EventManagement.Event.entity.Survey;
import EventManagement.Event.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping("/survey/{eventId}")
    public ResponseEntity<Survey> createSurvey(@PathVariable int eventId, @RequestBody SurveyDTO surveyDTO) {
        Survey createdSurvey = surveyService.createSurvey(eventId, surveyDTO);
        return ResponseEntity.ok(createdSurvey);
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long surveyId, @RequestBody SurveyDTO surveyDTO) {
        Survey updatedSurvey = surveyService.updateSurvey(surveyId, surveyDTO);
        return new ResponseEntity<>(updatedSurvey, HttpStatus.OK);
    }

    @GetMapping("/list-survey/{accountID}")
    public List<Survey> getSurveyByAccountID(@PathVariable int accountID) {
        return surveyService.getSurveyByAccountID(accountID);

    }
}
