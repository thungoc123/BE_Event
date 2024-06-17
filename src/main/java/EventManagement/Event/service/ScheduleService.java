package EventManagement.Event.service;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.EventSchedule;
import EventManagement.Event.payload.Request.InsertScheduleRequest;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.EventScheduleRepository;
import EventManagement.Event.service.imp.ScheduleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService implements ScheduleServiceImp{

   @Autowired
   private EventRepository eventRepository;


   @Autowired
   private EventScheduleRepository eventScheduleRepository;
    @Override
    public boolean insertSchedule(InsertScheduleRequest insertScheduleRequest) {

        int eventId = insertScheduleRequest.getEventId();
       Event event = eventRepository.findById(eventId).orElse(null);
        System.out.println("Fetching event with ID: " + eventId);
       if (event == null) {
           return false; // neu k tim thay eventid tra ve false
        }
        EventSchedule eventSchedule = new EventSchedule();
        eventSchedule.setEvent(event);
        eventSchedule.setName(insertScheduleRequest.getName());
        eventSchedule.setDate(insertScheduleRequest.getDate());
        eventSchedule.setTimestart(insertScheduleRequest.getTimeStart());
        eventSchedule.setDuration(insertScheduleRequest.getDuration());
        eventSchedule.setActor(insertScheduleRequest.getActor());
        eventSchedule.setDescription(insertScheduleRequest.getDescription());
        eventSchedule.setEventType(insertScheduleRequest.getEventType());
        eventSchedule.setLocation(insertScheduleRequest.getLocation());

        eventScheduleRepository.save(eventSchedule);

        return true;
    }
   }


