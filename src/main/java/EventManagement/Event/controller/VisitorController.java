package EventManagement.Event.controller;

import EventManagement.Event.DTO.VisitorRegistrationDTO;
import EventManagement.Event.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/sign-up-visitor")
    public ResponseEntity<String> signUpVisitor(@RequestBody VisitorRegistrationDTO visitorRegistrationDTO) {
        try {
            visitorService.signUpVisitor(visitorRegistrationDTO);
            return ResponseEntity.ok("Visitor signed up successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
