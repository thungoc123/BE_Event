package EventManagement.Event.service;

import EventManagement.Event.entity.Account;
import EventManagement.Event.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
