package EventManagement.Event.service;

import EventManagement.Event.DTO.SurveyDTO;
import EventManagement.Event.DTO.SurveyQuestionDTO;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Survey;
import EventManagement.Event.entity.SurveyQuestion;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.SurveyQuestionRepository;
import EventManagement.Event.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyQuestionService {

    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    public SurveyQuestion createSurveyQuestion(Long surveyId, SurveyQuestionDTO surveyDTO) {
        Optional<Survey> surveyQuestionOptional = surveyRepository.findById(surveyId);
        if (!surveyQuestionOptional.isPresent()) {
            throw new RuntimeException("Event not found with id: " + surveyId);
        }

        Survey survey = surveyQuestionOptional.get();


        SurveyQuestion surveyQuestion = new SurveyQuestion();
        surveyQuestion.setTypeQuestion(surveyDTO.getTypeQuestion());
        surveyQuestion.setDeleteAt(surveyDTO.getDeleteAt());
        surveyQuestion.setModifiedAt(surveyDTO.getModifiedAt());
        surveyQuestion.setSurvey(survey);

        return  surveyQuestionRepository.save(surveyQuestion);


    }
}

