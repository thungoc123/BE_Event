package EventManagement.Event.controller;

import EventManagement.Event.entity.Attendance;
import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.repository.AttendanceRepository;
import EventManagement.Event.service.AttendanceService;
import EventManagement.Event.service.CheckingStaffService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-staff")
public class CheckingStaffController {
    @Autowired
    private CheckingStaffService checkingStaffService;
    @Autowired
    private AttendanceService attendanceService;



    @GetMapping("/account/events")
    public ResponseEntity<List<CheckingStaff>> getEventsByAccount(HttpServletRequest request) {
        List<CheckingStaff> checkingStaffs = checkingStaffService.getEventsByAccountId(request);
        return ResponseEntity.ok(checkingStaffs);
    }
    @GetMapping("/account/attendance")
    public ResponseEntity<List<Attendance>> getAttendanceByAccount(HttpServletRequest request) {
        String accountId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Attendance> attendances = attendanceService.getAttendancesByCheckingStaffAccountId(accountId);
        return ResponseEntity.ok(attendances);
    }

}
