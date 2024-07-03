package EventManagement.Event.controller;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.payload.Request.AddEventsToSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.repository.SponsorProgramRepository;
import EventManagement.Event.service.EventService;
import EventManagement.Event.service.SponsorService;
import EventManagement.Event.DTO.SponsorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api-sponsor")
public class SponsorController {
    @Autowired
    private EventService  eventService;
    @Autowired
    private SponsorService sponsorService;
    @Autowired
    private SponsorProgramRepository sponsorProgramRepository;

    @PostMapping("/sponsorProgram/{sponsorProgramId}/events")
    public ResponseEntity<Map<String, String>> addEventsToSponsorProgram(@PathVariable int sponsorProgramId, @RequestBody AddEventsToSponsorProgramRequest request) {
        boolean isSuccess = sponsorService.addEventsToSponsorProgram(sponsorProgramId, request.getEventIds());
        Map<String, String> response = new HashMap<>();
        if (isSuccess) {
            response.put("message", "add event into program successfully");
            return ResponseEntity.ok(response);

        } else{
            response.put("message", "add event failed");
            return ResponseEntity.ok(response);
        }


    }

    @GetMapping("/account/event")
    public ResponseEntity<List<Event>> getEventsByAccount(HttpServletRequest request) {
        List<Event> events = eventService.getEventsBySponsorId(request);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/account/program")
    public ResponseEntity<List<SponsorProgram>> getProgramsByAccount(HttpServletRequest request) {
        List<SponsorProgram> sponsorPrograms = sponsorService.getProgramsByAccountId(request);
        return ResponseEntity.ok(sponsorPrograms);
    }
    @GetMapping("/program")

    public  ResponseEntity<List<SponsorProgram>> getAllSponsorPrograms() {
        List<SponsorProgram> programs = sponsorService.getAllSponsorPrograms();
        return ResponseEntity.ok(programs);
    }
    @GetMapping
    public ResponseEntity<List<SponsorDTO>> getAllSponsors() {
        List<SponsorDTO> sponsors = sponsorService.getAllSponsors();
        return ResponseEntity.ok(sponsors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SponsorDTO> getSponsorById(@PathVariable Long id) {
        Optional<SponsorDTO> sponsorOptional = sponsorService.getSponsorById(id);
        return sponsorOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/create-program")
    public ResponseEntity<Map<String, String>> createProgram(@RequestBody InsertSponsorProgramRequest insertSponsorProgramRequest){
        boolean isSuccess = sponsorService.insertSponsorProgram(insertSponsorProgramRequest);
        Map<String, String> response = new HashMap<>();
        if (isSuccess) {
            response.put("message", "Program created successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to create program");
            return ResponseEntity.status(500).body(response);
        }
    }
}
