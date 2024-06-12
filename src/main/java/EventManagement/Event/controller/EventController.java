package EventManagement.Event.controller;

import EventManagement.Event.entity.Event;
import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.service.EventService;
import EventManagement.Event.service.imp.EventServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api-events")
public class EventController {
    @Autowired
    private EventServiceImp eventServiceimp;
    @Autowired
    private EventService eventService;



    @GetMapping
    public List<Event> getAllEvent(){

        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventDetailsById(@PathVariable int id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public ResponseEntity<?> insertEvent(@ModelAttribute  InsertEventRequest request) {
        boolean isInserted = eventService.insertEvent(request);
        if (isInserted) {
            return ResponseEntity.ok("Event inserted successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to insert event");
        }
    }
}
