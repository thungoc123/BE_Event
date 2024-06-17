package EventManagement.Event.controller;

import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Event;
import EventManagement.Event.payload.Request.InsertCheckingStaffRequest;
import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.payload.Request.InsertImageRequest;
import EventManagement.Event.payload.Request.InsertScheduleRequest;
import EventManagement.Event.repository.AccountRepository;
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
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api-events")
public class EventController {
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



    public List<Event> getAllEvent() {

        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventDetailsById(@PathVariable int id) {
        return eventService.getEventById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> insertEvent(InsertEventRequest request) {
        boolean isInserted = eventService.insertEvent(request);

        if (isInserted) {
            return ResponseEntity.ok("Event inserted successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to insert event");
        }
    }

    @PostMapping("/{id}/create-schedule")
    public ResponseEntity<?> insertSchedule(@PathVariable int id,
                                            InsertScheduleRequest insertScheduleRequest) {

        Event event = eventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.status(400).body("Please create an event first");
        }
        insertScheduleRequest.setEventId(id);
        boolean isSuccess = scheduleService.insertSchedule(insertScheduleRequest);
        if (isSuccess) {
            return ResponseEntity.ok("Schedule created successfully for event with ID: " + id);
        } else {
            return ResponseEntity.status(500).body("Failed to create schedule");
        }
    }
    @PostMapping("/{id}/create-staff")
    public ResponseEntity<?> insertCheckingStaff(@PathVariable int id,

                                         @RequestBody InsertCheckingStaffRequest insertCheckingStaffRequest

                                         ){
        Event event = eventService.getEventById(id);
        String email = insertCheckingStaffRequest.getEmail();
        insertCheckingStaffRequest.setEventId(id);

        if (event == null) {
            return ResponseEntity.status(400).body("Please create an event first");
        }
        Account account = accountService.getAccountByEmail(email);
        if (account != null) {
            return ResponseEntity.status(400).body("Please check email");
        }

        boolean isSuccess = checkingStaffService.insertCheckingStaff(insertCheckingStaffRequest);
        if (isSuccess) {
            return ResponseEntity.ok("Checking staff added successfully.");
        } else {
            return ResponseEntity.status(400).body("Event not found or role not found.");
        }
    }
    @PostMapping("{id}/add-image")
    public ResponseEntity<?> insertImage(@PathVariable int id,
                                         @RequestBody InsertImageRequest insertImageRequest
                                         ){
        Event event = eventService.getEventById(id);
        insertImageRequest.setEventId(id);
        boolean isSuccess = imageService.insertImage(insertImageRequest);
        if (isSuccess) {
            return ResponseEntity.ok("Image added successfully.");
        } else {
            return ResponseEntity.status(400).body("Failed to add image");
        }


    }
}



