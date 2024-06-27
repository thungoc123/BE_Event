package EventManagement.Event.controller;

import EventManagement.Event.DTO.VisitorRegistrationDTO;
import EventManagement.Event.payload.BaseResponse;
import EventManagement.Event.payload.Request.VisitorResponseDTO;
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
    public ResponseEntity<VisitorResponseDTO> signUpVisitor(@RequestBody VisitorRegistrationDTO visitorRegistrationDTO) {
        try {
            visitorService.signUpVisitor(visitorRegistrationDTO);
            VisitorResponseDTO response = new VisitorResponseDTO("Đăng ký thành công", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            VisitorResponseDTO response = new VisitorResponseDTO(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
