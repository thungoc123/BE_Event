package EventManagement.Event.service;

import EventManagement.Event.entity.*;
import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.payload.Request.InsertScheduleRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;
import EventManagement.Event.repository.*;
import EventManagement.Event.service.imp.EventServiceImp;
//import EventManagement.Event.service.imp.FileServiceImp;
import EventManagement.Event.utils.JwtHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventService implements EventServiceImp {

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private StateEventRepository stateEventRepository;
    @Autowired
    private AccountRepository accountRepository;



    //list event
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }


    //insert event
    @Override
    public boolean insertEvent(InsertEventRequest request) {

        try {
            int accountId = request.getAccountId();

            Account account = accountRepository.findById(accountId);
            if (account == null) {
                throw new RuntimeException("Can't find accountId: " + request.getAccountId());
            }
            StateEvent stateEvent = stateEventRepository.findById(1);
            if (stateEvent == null) {
                throw new RuntimeException("Can't find StateEvent with id: " + 1);
            }
            Event eventEntity = new Event();
            eventEntity.setAccount(account);
            eventEntity.setStateEvent(stateEvent);
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

