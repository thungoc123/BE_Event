package EventManagement.Event.repository;

import EventManagement.Event.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SponsorRepository extends JpaRepository<Sponsor,Long> {

    List<Sponsor> findByAccountId(int accountId);
    Sponsor findById(int Id);
    Sponsor getSponsorById(int id);
    void deleteByAccountId(int accountId);
}

