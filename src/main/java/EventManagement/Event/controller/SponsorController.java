package EventManagement.Event.controller;

import EventManagement.Event.service.SponsorService;
import EventManagement.Event.DTO.SponsorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-sponsor")
public class SponsorController {
    @Autowired
    private SponsorService sponsorService;

    @GetMapping
    public ResponseEntity<List<SponsorDTO>> getAllSponsors() {
        List<SponsorDTO> sponsors = sponsorService.getAllSponsors();
        return ResponseEntity.ok(sponsors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SponsorDTO> getSponsorById(@PathVariable Long id) {
        Optional<SponsorDTO> sponsorOptional = sponsorService.getSponsorById(id);
        return sponsorOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
