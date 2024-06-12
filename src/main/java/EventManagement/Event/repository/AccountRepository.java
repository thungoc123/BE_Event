package EventManagement.Event.repository;

import EventManagement.Event.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    boolean existsByEmail(String email);
    Account findByEmail(String email);
}
