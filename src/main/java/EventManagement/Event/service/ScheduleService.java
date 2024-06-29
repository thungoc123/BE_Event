package EventManagement.Event.service;

import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.EventSchedule;
import EventManagement.Event.payload.Request.InsertScheduleRequest;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.EventScheduleRepository;
import EventManagement.Event.service.imp.ScheduleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService implements ScheduleServiceImp {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AccountRepository accountRepository;
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

    @Override
    public boolean updateSchedule(int eventId, int scheduleId, InsertScheduleRequest insertScheduleRequest) {

        try {
            Event event = eventRepository.findById(eventId).orElse(null);
            if (event == null) {
                throw new RuntimeException("Can't find eventId: " + eventId);
            }


            EventSchedule scheduleToUpdate = eventScheduleRepository.findById(scheduleId).orElse(null);
            if (scheduleToUpdate == null) {
                throw new RuntimeException("Can't find scheduleId: " + scheduleId);
            }
            scheduleToUpdate.setName(insertScheduleRequest.getName());
            scheduleToUpdate.setDate(insertScheduleRequest.getDate());
            scheduleToUpdate.setTimestart(insertScheduleRequest.getTimeStart());
            scheduleToUpdate.setDuration(insertScheduleRequest.getDuration());
            scheduleToUpdate.setActor(insertScheduleRequest.getActor());
            scheduleToUpdate.setDescription(insertScheduleRequest.getDescription());
            scheduleToUpdate.setEventType(insertScheduleRequest.getEventType());
            scheduleToUpdate.setLocation(insertScheduleRequest.getLocation());
            eventScheduleRepository.save(scheduleToUpdate);
            System.out.println("Schedule updated successfully for event with ID " + eventId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    @Override
    public boolean deleteSchedule( int scheduleId) {
        try {

            EventSchedule schedule = eventScheduleRepository.findById(scheduleId).orElse(null);
            if (schedule == null) {
                throw new RuntimeException("Can't find scheduleId: " + scheduleId);
            }
            eventScheduleRepository.delete(schedule);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


