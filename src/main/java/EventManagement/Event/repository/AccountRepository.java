package EventManagement.Event.repository;

import EventManagement.Event.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    boolean existsByEmail(String email);
    Account findByEmail(String email);

    Account findById(int id);

    Optional<Account> findById(Long id);
    List<Account> findAll();
}
