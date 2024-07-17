package EventManagement.Event.controller;

import EventManagement.Event.entity.Attendance;
import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.payload.EmailResponse;
import EventManagement.Event.payload.Request.EmailRequest;
import EventManagement.Event.repository.AttendanceRepository;
import EventManagement.Event.service.AttendanceService;
import EventManagement.Event.service.CheckingStaffService;
import EventManagement.Event.service.EmailService;
import EventManagement.Event.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-staff")
public class CheckingStaffController {
    @Autowired
    private CheckingStaffService checkingStaffService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private MailService mailService;



    @GetMapping("/account/events")
    public ResponseEntity<CheckingStaff> getEventsByAccount(HttpServletRequest request) {
        CheckingStaff checkingStaff = checkingStaffService.getEventsByAccountId(request);
        return ResponseEntity.ok(checkingStaff);
    }
    @GetMapping("/account/attendance")
    public ResponseEntity<List<Attendance>> getAttendanceByAccount(HttpServletRequest request) {
        String accountId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Attendance> attendances = attendanceService.getAttendancesByCheckingStaffAccountId(accountId);
        return ResponseEntity.ok(attendances);
    }
    @PostMapping("/send")
    public ResponseEntity<EmailResponse>  sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        mailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getFeedbackLink());
        EmailResponse response = new EmailResponse("success", "Email sent successfully to " + emailRequest.getTo());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
