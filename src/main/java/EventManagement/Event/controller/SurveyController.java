package EventManagement.Event.controller;

import EventManagement.Event.DTO.SurveyDTO;
import EventManagement.Event.entity.Survey;
import EventManagement.Event.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
