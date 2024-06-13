package EventManagement.Event.controller;

import EventManagement.Event.DTO.SponsorRegistrationDto;
import EventManagement.Event.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-sponsor")

public class RegistrationSponsorController {
    @Autowired
    private SponsorService sponsorService;

    @PostMapping("/sign-up-sponsor")
    public ResponseEntity<String> signUpVisitor(@RequestBody SponsorRegistrationDto sponsorSignUp) {
        try {
            sponsorService.registerSponsor(sponsorSignUp);
            return ResponseEntity.ok("Sponsor signed up successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

