package EventManagement.Event.repository;

import EventManagement.Event.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor,Long> {
    Sponsor findByfptStaffEmail(String email);

    Sponsor findById(int Id);
    Sponsor getSponsorById(int id);
    void deleteByAccountId(int accountId);
}

