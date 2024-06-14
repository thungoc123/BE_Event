//package EventManagement.Event.controller;
//
//
//import EventManagement.Event.payload.Request.InsertEventRequest;
//import EventManagement.Event.payload.Request.InsertScheduleRequest;
//import EventManagement.Event.service.EventService;
//import EventManagement.Event.service.ScheduleService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//@RestController
//@RequestMapping("/api-schedules")
//public class ScheduleController {
//
//    @Autowired
//    private EventService eventService;
//    @Autowired
//    private ScheduleService scheduleService;
//
//    @PostMapping("/create")
//    public ResponseEntity<?>  insertSchedule(@RequestParam Integer eventId,
//                                             @RequestParam String name,
//                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
//                                             @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") LocalTime timeStart,
//                                             @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") LocalTime duration,
//                                             @RequestParam String actor,
//                                             @RequestParam String description,
//                                             @RequestParam String eventType,
//                                             @RequestParam String location
//    ){
//
//        if (eventService.getEventById(eventId) == null) {
//            return ResponseEntity.status(400).body("Please! Create an event first");
//        }
//        InsertScheduleRequest insertScheduleRequest = new InsertScheduleRequest();
//        insertScheduleRequest.setEventId(eventId);
//        insertScheduleRequest.setName(name);
//        insertScheduleRequest.setDate(date);
//        insertScheduleRequest.setTimeStart(timeStart);
//        insertScheduleRequest.setDuration(duration);
//        insertScheduleRequest.setActor(actor);
//        insertScheduleRequest.setDescription(description);
//        insertScheduleRequest.setEventType(eventType);
//        insertScheduleRequest.setLocation(location);
//
//        if(eventService.getEventById(eventId) == null){
//            return ResponseEntity.status(400).body("Please! Create an event first");
//        }
//        insertScheduleRequest.setEventId(eventId);
//        boolean isSuccess = scheduleService.insertSchedule(insertScheduleRequest);
//        if (isSuccess) {
//            return ResponseEntity.ok("Schedule created successfully for event with ID: " + insertScheduleRequest.getEventId());
//        } else {
//            return ResponseEntity.status(500).body("Failed to create schedule");
//        }
//
//
//
//
//    }
//}
