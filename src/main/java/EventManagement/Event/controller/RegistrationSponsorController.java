package EventManagement.Event.controller;

import EventManagement.Event.DTO.SponsorRegistrationDto;
import EventManagement.Event.payload.Request.VisitorResponseDTO;
import EventManagement.Event.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-sponsor")

public class RegistrationSponsorController {
    @Autowired
    private SponsorService sponsorService;

    @PostMapping("/sign-up-sponsor")
    public ResponseEntity<VisitorResponseDTO> signUpVisitor(@RequestBody SponsorRegistrationDto sponsorSignUp) {
        try {
            sponsorService.registerSponsor(sponsorSignUp);
            VisitorResponseDTO response = new VisitorResponseDTO("Đăng ký thành công", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            VisitorResponseDTO response = new VisitorResponseDTO(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

