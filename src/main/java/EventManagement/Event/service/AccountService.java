package EventManagement.Event.service;

import EventManagement.Event.entity.Account;
import EventManagement.Event.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public Account getAccountByEmail(String email) {return accountRepository.findByEmail(email);}
    public Account getAccountById(int id) {return accountRepository.findById(id);}
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


}
