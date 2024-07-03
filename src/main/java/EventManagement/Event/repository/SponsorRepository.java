package EventManagement.Event.repository;

import EventManagement.Event.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SponsorRepository extends JpaRepository<Sponsor,Long> {
    Sponsor findByfptStaffEmail(String email);

    Optional<Sponsor> findById(Long Id);
    Sponsor getSponsorById(int id);
    void deleteByAccountId(int accountId);
}

