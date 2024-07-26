package EventManagement.Event.controller;

import EventManagement.Event.DTO.SurveyDTO;
import EventManagement.Event.DTO.SurveyQuestionDTO;
import EventManagement.Event.entity.Survey;
import EventManagement.Event.entity.SurveyQuestion;
import EventManagement.Event.service.SurveyQuestionService;
import EventManagement.Event.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-surveyQuestion")
public class SurveyQuestionController {
    @Autowired
    private SurveyQuestionService surveyQuestionService;

    @PostMapping("/surveyquestion/{surveyId}")
    public ResponseEntity<SurveyQuestion> createSurvey(@PathVariable Long surveyId, @RequestBody SurveyQuestionDTO surveyQuestionDTO) {
        SurveyQuestion createdSurveyQuestion = surveyQuestionService.createSurveyQuestion(surveyId, surveyQuestionDTO);
        return ResponseEntity.ok(createdSurveyQuestion);
    }
}
