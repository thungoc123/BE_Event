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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class EventService implements EventServiceImp {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private StateEventRepository stateEventRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingStaffService checkingStaffService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ScheduleService scheduleService;


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
            return false;
        }
    }

    public List<Event> getEventsByAccountId(HttpServletRequest request) {
        String accountId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            return eventRepository.findByAccountId(Integer.parseInt(accountId));


        }
        public List<Event> getEventsByStateId ( int stateEventId){
            return eventRepository.findByStateEventId(stateEventId);
        }
        @Override
        public boolean updateEvent ( int eventId, InsertEventRequest request){
            try {
                Event event = eventRepository.findById(eventId).orElse(null);
                if (event == null) {
                    throw new RuntimeException("Can't find eventId: " + eventId);
                }




                event.setDescription(request.getDescription());
                event.setName(request.getEventName());
                event.setTimestart(request.getTimeStart());
                event.setTimeend(request.getTimeEnd());
                event.setPrice(request.getPrice());
                event.setTimeopensale(request.getTimeOpenSale());
                event.setTimeclosesale(request.getTimeCloseSale());
                Event eventUpdated = eventRepository.save(event);
                return eventUpdated != null;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        @Override
        public boolean deleteEvent(int eventId) {
            try {
                Event eventToDelete  = eventRepository.findById(eventId).orElse(null);
                if (eventToDelete == null) {
                    throw new RuntimeException("Can't find eventId: " + eventId);
                }
                eventRepository.deleteSponsorProgramEventByEventId(eventId);
                checkingStaffService.deleteAllCheckingStaff(eventId);
                imageService.deleteImagebyEvent(eventId);
                scheduleService.deleteSchedulebyEvent(eventId);
                

                eventRepository.delete(eventToDelete);

                System.out.println("Deleting event with ID " + eventId);
                return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
            }
        }
    }


