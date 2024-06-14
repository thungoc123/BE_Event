package EventManagement.Event.service;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.EventImage;
import EventManagement.Event.entity.EventSchedule;
import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.payload.Request.InsertScheduleRequest;
import EventManagement.Event.repository.EventImageRepository;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.EventScheduleRepository;
import EventManagement.Event.repository.StateRepository;
import EventManagement.Event.service.imp.EventServiceImp;
//import EventManagement.Event.service.imp.FileServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements EventServiceImp {


    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventScheduleRepository eventScheduleRepository;
    @Autowired
    private ObjectMapper objectMapper;

    //list event
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }


    //insert event
    @Override
    public Boolean insertEvent(InsertEventRequest request) {

        try {
            Event eventEntity = new Event();
            eventEntity.setDescription(request.getDescription());
            eventEntity.setName(request.getEventName());
            eventEntity.setTimestart(request.getTimeStart());
            eventEntity.setTimeend(request.getTimeEnd());
            eventEntity.setPrice(request.getPrice());
            eventEntity.setTimeopensale(request.getTimeOpenSale());
            eventEntity.setTimeclosesale(request.getTimeCloseSale());
            Event eventSaved = eventRepository.save(eventEntity);

            return eventSaved != null; // Trả về true nếu lưu thành công, false nếu không thành công
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có lỗi xảy ra
            return false; // Trả về false nếu có lỗi xảy ra
        }


    }
}