package EventManagement.Event.service;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.EventDetails;
import EventManagement.Event.entity.EventImage;
import EventManagement.Event.entity.EventSchedule;
import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.payload.Request.InsertScheduleRequest;
import EventManagement.Event.repository.EventDetailsRepository;
import EventManagement.Event.repository.EventImageRepository;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.EventScheduleRepository;
import EventManagement.Event.service.imp.EventServiceImp;
import EventManagement.Event.service.imp.FileServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements EventServiceImp {
    @Autowired
    private FileServiceImp fileServiceimp;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventImageRepository eventImageRepository;
    @Autowired
    private EventDetailsRepository eventDetailsRepository;
    @Autowired
    private EventScheduleRepository eventScheduleRepository;
    @Autowired
    private ObjectMapper objectMapper;


    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public boolean insertEvent(InsertEventRequest request) {
        boolean isCopySuccess = fileServiceimp.saveFile(request.getFile());

        if(isCopySuccess){
            try {
                List<InsertScheduleRequest> schedules = objectMapper.readValue(request.getSchedules(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, InsertScheduleRequest.class));
            Event eventEntity = new Event();
            eventEntity.setDescription(request.getDescription());
            eventEntity.setName(request.getEventName());
            eventEntity.setTimestart(request.getTimeStart());
            eventEntity.setTimeend(request.getTimeEnd());
            eventEntity.setPrice(request.getPrice());

            Event eventSaved = eventRepository.save(eventEntity);

            EventImage eventImage = new EventImage();
            eventImage.setName(request.getFile().getOriginalFilename());
            eventImage.setEvent(eventSaved);
            eventImageRepository.save(eventImage);

            EventDetails eventDetails = new EventDetails();
            eventDetails.setClient(request.getClient());
            eventDetails.setDate(request.getDate());
            eventDetails.setTime(request.getTime());
            eventDetails.setLocation(request.getLocation());
            eventDetails.setEvent(eventSaved);
            eventDetails.setDuration(request.getDuration());
            eventDetailsRepository.save(eventDetails);

                schedules.forEach(scheduleRequest -> {
                    EventSchedule eventSchedule = new EventSchedule();
                    eventSchedule.setDetails(scheduleRequest.getDetails());
                    eventSchedule.setActor(scheduleRequest.getActor());
                    eventSchedule.setTime(scheduleRequest.getTime());
                    eventSchedule.setPlace(scheduleRequest.getPlace());
                    eventSchedule.setEventDetails(eventDetails);
                    eventScheduleRepository.save(eventSchedule);
                });

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }






        }
        return false;
    }
}