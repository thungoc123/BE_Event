package EventManagement.Event.controller;

import EventManagement.Event.DTO.AttendanceDTO;
import EventManagement.Event.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-attendances-2")
public class AttendanceController2 {

    @Autowired
    private AttendanceService attendanceService;
    @GetMapping("/list/event/{eventId}")
    public List<AttendanceDTO> getAttendancesByEventId2(@PathVariable int eventId) {
        return attendanceService.getAttendancesByEventId2(eventId);
    }

}
