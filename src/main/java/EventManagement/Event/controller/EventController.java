package EventManagement.Event.controller;

import EventManagement.Event.DTO.SponsorDTO;
import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Event;
import EventManagement.Event.payload.Request.*;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.SponsorRepository;
import EventManagement.Event.service.*;
import EventManagement.Event.service.imp.EventServiceImp;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api-events")
public class EventController {
    @Autowired
    private SponsorService sponsorService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EventService eventService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CheckingStaffService checkingStaffService;
    @Autowired
    private ImageService imageService;



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
                                            InsertScheduleRequest insertScheduleRequest) {

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
        String email = insertCheckingStaffRequest.getEmail();
        insertCheckingStaffRequest.setEventId(id);

        Map<String, String> response = new HashMap<>();
        if (event == null) {
            response.put("message", "Please create an event first");
            return ResponseEntity.status(400).body(response);
        }
        Account account = accountService.getAccountByEmail(email);
        if (account != null) {
            response.put("message", "Please check email");
            return ResponseEntity.status(400).body(response);
        }

        boolean isSuccess = checkingStaffService.insertCheckingStaff(insertCheckingStaffRequest);
        if (isSuccess) {
            response.put("message", "Checking staff added successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Event not found or role not found.");
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
        if (isSuccess) {
            response.put("message", "Sponsor added successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Event already has a sponsor.");
            return ResponseEntity.status(400).body(response);
        }
    }
}



