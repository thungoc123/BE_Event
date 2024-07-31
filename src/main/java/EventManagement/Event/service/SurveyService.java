package EventManagement.Event.service;

import EventManagement.Event.DTO.FeedbackEventDTO;
import EventManagement.Event.DTO.SurveyDTO;
import EventManagement.Event.DTO.SurveyEventDTO;
import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Survey;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SurveyService {
    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Survey createSurvey(int eventId, SurveyDTO surveyDTO) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (!eventOptional.isPresent()) {
            throw new RuntimeException("Event not found with id: " + eventId);
        }

        Event event = eventOptional.get();

        Survey survey = new Survey();
        survey.setTitle(surveyDTO.getTitle());
        survey.setTarget(surveyDTO.getTarget());
        survey.setModifiedAt(surveyDTO.getModifiedAt());
        survey.setDeleteAt(surveyDTO.getDeleteAt());
        survey.setEvent(event);

        return surveyRepository.save(survey);
    }

    public Survey updateSurvey(Long surveyId, SurveyDTO surveyDTO) {
        Optional<Survey> surveyOptional = surveyRepository.findById(surveyId);

        if (!surveyOptional.isPresent()) {
            throw new RuntimeException("Survey not found with id: " + surveyId);
        }

        Survey survey = surveyOptional.get();
        survey.setTitle(surveyDTO.getTitle());
        survey.setTarget(surveyDTO.getTarget());
        survey.setModifiedAt(surveyDTO.getModifiedAt());
        survey.setDeleteAt(surveyDTO.getDeleteAt());

        return surveyRepository.save(survey);
    }

//    public List<Survey> getSurveyByAccountID(int accountID) {
//
//        return surveyRepository.findByAccountId(accountID);
//    }
public List<SurveyEventDTO> getSurveyByAccountID(int accountID) {
    return surveyRepository.findByAccountId(accountID);
}
}

