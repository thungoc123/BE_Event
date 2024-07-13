package EventManagement.Event.controller;

import EventManagement.Event.DTO.AttendanceDTO;
import EventManagement.Event.entity.Attendance;
import EventManagement.Event.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-attendance")
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/create")
    public ResponseEntity<?> createAttendances(@RequestParam int eventId) {
        try {
            List<Attendance> attendances = attendanceService.createAttendancesForEvent(eventId);
            return ResponseEntity.ok(attendances);
        } catch (RuntimeException e) {
            log.error("Error creating attendances for event ID: {}", eventId, e);
            return ResponseEntity.status(500).body("{\"message\": \"Error creating attendances for event ID: " + eventId + "\"}");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAttendanceStatus(@RequestParam int ticketId, @RequestParam Attendance.AttendanceStatus status) {
        try {
            Attendance updatedAttendance = attendanceService.updateAttendanceStatus(ticketId, status);
            return ResponseEntity.ok(updatedAttendance);
        } catch (IllegalArgumentException e) {
            log.error("Error updating attendance status for ticket ID: {}", ticketId, e);
            return ResponseEntity.status(404).body("{\"message\": \"Invalid Ticket ID: " + ticketId + "\"}");
        } catch (RuntimeException e) {
            log.error("Unexpected error updating attendance status for ticket ID: {}", ticketId, e);
            return ResponseEntity.status(500).body("{\"message\": \"Unexpected error updating attendance status for ticket ID: " + ticketId + "\"}");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        if (attendances == null || attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Data is null");
        }
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Object> getAttendancesByEventId(@PathVariable int eventId) {
        List<Attendance> attendances = attendanceService.getAttendancesByEventId(eventId);
        if (attendances == null || attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Id not found");
        }
        return ResponseEntity.ok(attendances);

    }

    @GetMapping("/list/event/{eventId}")
    public List<AttendanceDTO> getAttendancesByEventId2(@PathVariable int eventId) {
        return attendanceService.getAttendancesByEventId2(eventId);
    }
}
