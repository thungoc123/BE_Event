package EventManagement.Event.controller;

import EventManagement.Event.DTO.AttendanceRequestDTO;
import EventManagement.Event.entity.Attendance;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/createListByEvent/{eventId}")
    public ResponseEntity<Void> createAttendanceList(@PathVariable int eventId, @RequestBody List<AttendanceRequestDTO> attendanceRequests) {
        attendanceService.createAttendanceList(eventId, attendanceRequests);
        return ResponseEntity.ok().build();
    }
}
