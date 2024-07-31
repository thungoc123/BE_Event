package EventManagement.Event.controller;

import EventManagement.Event.DTO.SponsorProfitDTO;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.payload.Request.AddEventsToSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.service.EventService;
import EventManagement.Event.service.SponsorEventService;
import EventManagement.Event.service.SponsorService;
import EventManagement.Event.DTO.SponsorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api-sponsor")
public class SponsorController {
    @Autowired
    private EventService  eventService;
    @Autowired
    private SponsorService sponsorService;
    @Autowired
    private SponsorEventService sponsorEventService;


    @PostMapping("/sponsorProgram/{sponsorProgramId}/events")
    public ResponseEntity<Map<String, String>> addEventsToSponsorProgram(@PathVariable int sponsorProgramId, @RequestBody AddEventsToSponsorProgramRequest request) {

        boolean isSuccess = sponsorService.addEventsToSponsorProgram(request );
        Map<String, String> response = new HashMap<>();
        if (isSuccess) {
            response.put("message", "add event into program successfully");
            return ResponseEntity.ok(response);

        } else{
            response.put("message", "add event failed");
            return ResponseEntity.ok(response);
        }


    }
    @GetMapping("/event/{eventId}/contributed-capital-percentage")
    public ResponseEntity<?> getContributedCapitalPercentage(@PathVariable int eventId) {
        try {
            double percentage = sponsorEventService.getTotalContributedCapitalPercentage(eventId);
            Map<String, Double> response = Collections.singletonMap("percentage", percentage);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }


    @PutMapping("/program/{sponsorProgramid}")
        public ResponseEntity<Map<String, String>> updateProgram(@PathVariable int sponsorProgramid,
                                                                @RequestBody InsertSponsorProgramRequest insertSponsorProgramRequest){
        boolean isUpdate = sponsorService.updateProgram(sponsorProgramid, insertSponsorProgramRequest);
        Map<String, String> response = new HashMap<>();

        if (isUpdate) {
            response.put("message", "update program successfully");
            return ResponseEntity.ok(response);
        } else{
            response.put("message", "update program failed");
            return ResponseEntity.ok(response);
        }


        }


   @GetMapping("/sponsor/{accountId}")
   public ResponseEntity<List<Sponsor>> getSponsorByAccountId(@PathVariable int accountId){
        List<Sponsor> sponsorDTOS = sponsorService.getSponsorsByAccountId(accountId);
        return ResponseEntity.ok(sponsorDTOS);
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
    @DeleteMapping("/program/{sponsorProgramId}/event/{eventID}")
    public ResponseEntity<Map<String, String>> removeEventFromSponsorProgram(@PathVariable int sponsorProgramId, @PathVariable int eventID){
        boolean isDelete = sponsorService.removeEventFromSponsorProgram(sponsorProgramId, eventID);
        Map<String, String> response = new HashMap<>();
        if (isDelete) {
            response.put("message", "event deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to remove event from the program");
            return ResponseEntity.status(500).body(response);
        }
    }
    @DeleteMapping("/program/{sponsorProgramId}")
    public ResponseEntity<Map<String, String>> deleteProgram(@PathVariable int sponsorProgramId){
        boolean isDelete = sponsorService.deleteProgram(sponsorProgramId);
        Map<String, String> response = new HashMap<>();
        if (isDelete) {
            response.put("message", "program deleted successfully");
            return ResponseEntity.ok(response);
        } else {

            response.put("message", "Failed to delete program");
            return ResponseEntity.status(500).body(response);
        }
    }
    @GetMapping("/{eventId}/sponsors/profits")
    public ResponseEntity<List<SponsorProfitDTO>> getSponsorProfitsByEventId(
            @PathVariable("eventId") int eventId,
            @RequestParam(required = false) Double totalEventProfit) {

        List<SponsorProfitDTO> sponsorProfits;
        if (totalEventProfit != null) {
            sponsorProfits = sponsorService.getSponsorProfitsByEventId(eventId, totalEventProfit);
        } else {
            sponsorProfits = sponsorService.getSponsorProfitsByEventId(eventId,totalEventProfit);
        }

        return ResponseEntity.ok().body(sponsorProfits);
    }
    @GetMapping("/{eventId}/{accountId}")
    public List<SponsorProfitDTO> getSponsorProfitsByEventId(
            @PathVariable int eventId,
            @PathVariable int accountId,
            @RequestParam Double totalEventProfit) {
        return sponsorService.getSponsorProfitsByEventIdAndAccountId(eventId, accountId, totalEventProfit);
    }
    @GetMapping("/profits/{accountId}")
    public List<SponsorProfitDTO> getSponsorProfitsByAccountId(
            @PathVariable int accountId) {
        return sponsorService.getSponsorProfitsByAccountId(accountId);
    }
}
