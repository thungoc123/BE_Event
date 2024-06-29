package EventManagement.Event.controller;

import EventManagement.Event.DTO.EventOperatorDTO;
import EventManagement.Event.DTO.SponsorRegistrationDto;
import EventManagement.Event.payload.Request.VisitorResponseDTO;
import EventManagement.Event.service.EventOperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-event-operators")
public class EventOperatorController {
    @Autowired
    private EventOperatorService eventOperatorService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signupEventOperator(@RequestBody EventOperatorDTO eventOperatorDTO) {
        try {
            eventOperatorService.signupEventOperator(eventOperatorDTO);
            VisitorResponseDTO response = new VisitorResponseDTO("Đăng ký thành công", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            VisitorResponseDTO response = new VisitorResponseDTO(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}


