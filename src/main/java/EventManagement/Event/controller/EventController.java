package EventManagement.Event.controller;

import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.StateEvent;
import EventManagement.Event.payload.DeleteResponse;
import EventManagement.Event.payload.Request.*;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.SponsorRepository;
import EventManagement.Event.repository.StateEventRepository;
import EventManagement.Event.service.*;

import EventManagement.Event.service.imp.EventServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-events")
public class EventController {
    @Autowired
    private SponsorService sponsorService;

    @Autowired
    private EventService eventService;
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CheckingStaffService checkingStaffService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private StateEventRepository stateEventRepository;

    @PatchMapping("/event/{eventId}/publish")
    public ResponseEntity<Map<String, String>> publishEvent(@PathVariable int eventId){

        Map<String, String> response = new HashMap<>();
        boolean isChanged = eventService.changeStateEvent(eventId);
        if (isChanged) {
            response.put("message", "Event has been changed.");
            return ResponseEntity.ok(response);
        } else{
            response.put("message", "Event has not been published.");
            return ResponseEntity.ok(response);
        }
    }
    @PostMapping("/{eventId}/callcapital")
    public ResponseEntity<Map<String, String>> callCapital(@PathVariable int eventId, @RequestBody CallCapitalRequest capitalRequest){
        Map<String, String> response = new HashMap<>();
        boolean isCalled = eventService.callCapital(eventId, capitalRequest);
        if (isCalled) {
            response.put("message", "Event has been called.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Event has not been called.");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/account")
    public ResponseEntity<List<Event>> getEventsByAccount(HttpServletRequest request) {
        List<Event> events = eventService.getEventsByAccountId(request);
        return ResponseEntity.ok(events);
    }
    @GetMapping("/state/{stateEventId}")
    public ResponseEntity<List<Event>> getEventsByState(@PathVariable int stateEventId) {
        List<Event> events =    eventService.getEventsByStateId(stateEventId);
        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping

    public  ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public Event getEventDetailsById(@PathVariable int id) {
        return eventService.getEventById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> insertEvent(@RequestBody InsertEventRequest request) {
        boolean isInserted = eventService.insertEvent(request);

        Map<String, String> response = new HashMap<>();
        if (isInserted) {
            response.put("message", "Event inserted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to insert event");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/{id}/create-schedule")
    public ResponseEntity<Map<String, String>> insertSchedule(@PathVariable int id,
                                            @RequestBody InsertScheduleRequest insertScheduleRequest) {

        Event event = eventService.getEventById(id);
        Map<String, String> response = new HashMap<>();

        if (event == null) {
            response.put("message", "Please create an event first");
            return ResponseEntity.status(400).body(response);
        }

        insertScheduleRequest.setEventId(id);
        boolean isSuccess = scheduleService.insertSchedule(insertScheduleRequest);

        if (isSuccess) {
            response.put("message", "Schedule created successfully for event with ID: " + id);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to create schedule");
            return ResponseEntity.status(500).body(response);
        }
    }
    @PostMapping("/{id}/create-staff")
    public ResponseEntity<Map<String, String>> insertCheckingStaff(@PathVariable int id,

                                         @RequestBody InsertCheckingStaffRequest insertCheckingStaffRequest

                                         ){
        Event event = eventService.getEventById(id);

        insertCheckingStaffRequest.setEventId(id);

        Map<String, String> response = new HashMap<>();
        if (event == null) {
            response.put("message", "Please create an event first");
            return ResponseEntity.status(400).body(response);
        }

        boolean isSuccess = checkingStaffService.insertCheckingStaff(insertCheckingStaffRequest);
        if (isSuccess) {
            response.put("message", "Checking staff added successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Event not found or account has been registered.");
            return ResponseEntity.status(400).body(response);
        }
    }
    @PostMapping("{id}/add-image")
    public ResponseEntity<Map<String, String>> insertImage(@PathVariable int id,
                                                           @RequestBody InsertImageRequest insertImageRequest
                                         ){
        Event event = eventService.getEventById(id);
        insertImageRequest.setEventId(id);
        boolean isSuccess = imageService.insertImage(insertImageRequest);
        Map<String, String> response = new HashMap<>();
        if (event == null) {
            response.put("message", "Please create an event first");
            return ResponseEntity.status(400).body(response);
        }
        if (isSuccess) {
            response.put("message", "Image added successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to add image.");
            return ResponseEntity.status(400).body(response);
        }


    }
    @PostMapping("{id}/add-sponsor")
    public ResponseEntity<Map<String, String>> insertSponsor(@PathVariable int id,
                                                           @RequestBody InsertSponsorRequest insertSponsorRequest
    ){
        Event event = eventService.getEventById(id);
        insertSponsorRequest.setEventId(id);
        boolean isSuccess = sponsorService.insertSponsor(insertSponsorRequest);
        Map<String, String> response = new HashMap<>();
        if (event == null) {
            response.put("message", "Please create an event first");
            return ResponseEntity.status(400).body(response);
        }
        if (isSuccess) {
            response.put("message", "Sponsor added successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Event already has a sponsor.");
            return ResponseEntity.status(400).body(response);
        }
    }
    @PutMapping("/{eventId}/schedules/{scheduleId}")
    public ResponseEntity<Map<String, String>> updateSchedule(@PathVariable int eventId, @PathVariable int scheduleId, @RequestBody InsertScheduleRequest insertScheduleRequest) {

            boolean isUpdated = scheduleService.updateSchedule(eventId, scheduleId, insertScheduleRequest);
            Map<String, String> response = new HashMap<>();
            if (isUpdated) {
                response.put("message", "Schedule updated successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Failed to update schedule");
                return ResponseEntity.status(400).body(response);
            }
    }
    @PutMapping("/add-sponsor/")
    public ResponseEntity<Map<String, String>> putSponsor(@RequestBody InsertSponsorRequest insertSponsorRequest){
        boolean result = sponsorService.putSponsor(insertSponsorRequest);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Sponsor added successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to add sponsor");
            return ResponseEntity.status(400).body(response);
        }
    }
    @PutMapping("/{eventId}")
    public ResponseEntity<Map<String, String>> updateEvent(@PathVariable int eventId, @RequestBody InsertEventRequest request) {
        boolean isUpdated = eventService.updateEvent(eventId, request);
        Map<String, String> response = new HashMap<>();
        if (isUpdated) {
            response.put("message", "Updated event successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to update event");
            return ResponseEntity.status(500).body(response);
        }
    }
    @DeleteMapping("/staff{checkingStaffId}/event{eventId}")
    public ResponseEntity<Map<String, String>> deleteCheckingStaff(@PathVariable int checkingStaffId,@PathVariable int eventId) {
        boolean isDeleted = checkingStaffService.deleteCheckingStaff(checkingStaffId, eventId);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Checking staff deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to delete checking staff");
            return ResponseEntity.status(500).body(response);
        }
    }
    @DeleteMapping("/{eventId}/sponsor/{sponsorId}")
    public ResponseEntity<Map<String, String>> deleteSponsor(@PathVariable int eventId, @PathVariable Long sponsorId) {
        boolean isDeleted = sponsorService.deleteSponsor(eventId, sponsorId);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Sponsor deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to delete sponsor");
            return ResponseEntity.status(500).body(response);
        }
    }
    @DeleteMapping("/image{imageId}")
    public ResponseEntity<Map<String, String>> deleteImage(@PathVariable int imageId) {
        boolean isDeleted = imageService.deleteImage(imageId);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Image deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to delete image");
            return ResponseEntity.status(500).body(response);
        }

    }
    @DeleteMapping("/schedule{scheduleId}")
    public ResponseEntity<Map<String, String>> deleteSchedule(@PathVariable int scheduleId) {
        boolean isDeleted = scheduleService.deleteSchedule(scheduleId);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Schedule deleted successfully");
            return ResponseEntity.ok(response);

        } else{
            response.put("message", "Failed to delete schedule");
            return ResponseEntity.status(500).body(response);
        }

    }
    @DeleteMapping("/event{eventId}")
    public ResponseEntity<Map<String, String>> deleteEvent(@PathVariable int eventId){
        boolean isDeleted = eventService.deleteEvent(eventId);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Event deleted successfully");
            return ResponseEntity.ok(response);
        } else{
            response.put("message", "Failed to delete event");
            return ResponseEntity.status(500).body(response);
        }


    }
//    @DeleteMapping("/delete/{eventId}")
//    public ResponseEntity<Object> deleteEvent2(@PathVariable int eventId) {
//        try {
//            eventService.deleteEventById(eventId);
//            return ResponseEntity.ok(new DeleteResponse("delete feedback successfully"));
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to delete event with ID " + eventId + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}



