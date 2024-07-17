package EventManagement.Event.repository;

import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor,Integer> {
    void deleteByAccountId(int accountId);
    Optional<Visitor> findByAccount_Id(int accountId);
    List<Visitor> findByAccountId(int accountId);
}
