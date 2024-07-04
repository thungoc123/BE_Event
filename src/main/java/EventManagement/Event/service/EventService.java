package EventManagement.Event.service;

import EventManagement.Event.entity.*;
import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.repository.*;
import EventManagement.Event.service.imp.EventServiceImp;
//import EventManagement.Event.service.imp.FileServiceImp;
import EventManagement.Event.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService implements EventServiceImp {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private StateEventRepository stateEventRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingStaffService checkingStaffService;
    @Autowired
    private EventImageRepository eventImageRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private SponsorRepository sponsorRepository;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private SponsorEventRepository sponsorEventRepository;
    @Autowired
    private VisitorAnswerRepository visitorAnswerRepository;

    @Autowired
    private FeedBackQuestionRepository feedbackQuestionRepository;







    //list event
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    private List<Long> getSponsorIdsByAccountId(int accountId) {
        List<Sponsor> accountSponsors = sponsorRepository.findByAccountId(accountId);
        return accountSponsors.stream()
                .map(Sponsor::getId)
                .collect(Collectors.toList());
    }

    public List<Event> getEventsBySponsorId(HttpServletRequest request) {
        try {


            String accountId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Long> sponsorIds = getSponsorIdsByAccountId(Integer.parseInt(accountId));

            List<Event> events = new ArrayList<>();
            for (Long sponsorId : sponsorIds) {
                List<SponsorEvent> sponsorEvents = sponsorEventRepository.findBySponsorId(sponsorId);
                for (SponsorEvent sponsorEvent : sponsorEvents) {
                    events.add(eventRepository.findById(sponsorEvent.getEventId())
                            .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + sponsorEvent.getEventId())));
                }
            }
            return events;
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
            return Collections.emptyList();
        }
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

                eventRepository.deleteBySponsorByEventId(eventId);
                eventRepository.deleteSponsorProgramEventByEventId(eventId);
                checkingStaffService.deleteAllCheckingStaff(eventId);
                List<EventImage> eventImages = eventImageRepository.findByEventId(eventId);
                for (EventImage eventImage : eventImages) {
                    eventImageRepository.delete(eventImage);
                }
                scheduleService.deleteSchedulebyEvent(eventId);


                eventRepository.delete(eventToDelete);

                System.out.println("Deleting event with ID " + eventId);
                return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
            }

        }
    @Transactional
    public void deleteEventById(int eventId) {
        // Lấy tất cả Feedback liên quan đến Event
        List<Feedback> feedbacks = feedbackRepository.findByEvent_Id(eventId);

        for (Feedback feedback : feedbacks) {
            int feedbackId = feedback.getFeedbackID();

            // Xóa tất cả VisitorAnswer liên quan đến Feedback
            List<VisitorAnswer> visitorAnswers = visitorAnswerRepository.findByFeedbackQuestion_Feedback_FeedbackID(feedbackId);
            visitorAnswerRepository.deleteInBatch(visitorAnswers);

            // Xóa tất cả FeedbackQuestion liên quan đến Feedback
            List<FeedbackQuestion> feedbackQuestions = feedbackQuestionRepository.findByFeedback_FeedbackID(feedbackId);
            feedbackQuestionRepository.deleteInBatch(feedbackQuestions);

            // Xóa Feedback
            feedbackRepository.deleteById(feedbackId);
        }

        // Xóa Event
        eventRepository.deleteById(eventId);
    }
        @Override
        public boolean changeStateEvent(int eventId){
            try {
                Event eventToChange  = eventRepository.findById(eventId).orElse(null);
                if (eventToChange == null) {
                    throw new RuntimeException("Can't find eventId: " + eventId);
                }
                StateEvent stateEvent = stateEventRepository.findById(2);

                if (eventToChange.getStateEvent().getId() == stateEvent.getId()) {
                    System.out.println("Event is already published.");
                    return true;
                }
                eventToChange.setStateEvent(stateEvent);
                eventRepository.save(eventToChange);
                System.out.println("Event state changed to publish successfully.");
                return true;
            } catch (Exception e){
               e.printStackTrace();
               return false;
            }
        }

        public Optional<Event> findById(int id) {
            return eventRepository.findById(id);
        }
    }


