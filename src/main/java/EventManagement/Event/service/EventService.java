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
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SponsorRepository sponsorRepository;


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
            Event eventEntity = new Event();
            eventEntity.setAccount(account);
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
    @Override
    public boolean insertSponsor(InsertSponsorRequest insertSponsorRequest) {

        try {
            int eventId = insertSponsorRequest.getEventId();
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + eventId));

            String email = insertSponsorRequest.getEmail();
            Sponsor sponsor = sponsorRepository.findByfptStaffEmail(email);
            if (sponsor == null) {
                throw new RuntimeException("Account not found");
            }
            if (event.getSponsor() != null) {
                throw new RuntimeException("Event already has a sponsor");
            }

            event.setSponsor(sponsor);
            eventRepository.save(event);

            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
            return false;
        }


    }



    }

