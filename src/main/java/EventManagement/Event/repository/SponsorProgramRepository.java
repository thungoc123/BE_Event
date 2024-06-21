package EventManagement.Event.repository;

import EventManagement.Event.entity.SponsorProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorProgramRepository extends JpaRepository<SponsorProgram, Integer> {
    boolean existsByTitle(String title);
}
