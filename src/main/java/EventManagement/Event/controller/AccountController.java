package EventManagement.Event.controller;

import EventManagement.Event.entity.Account;
import EventManagement.Event.payload.DeleteResponse;
import EventManagement.Event.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    @PutMapping("/{id}/disable")
    public ResponseEntity<?> disableAccount(@PathVariable int id) {
        accountService.disableAccount(id);
        return ResponseEntity.ok(new DeleteResponse(" đã ban account"));
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<?> enableAccount(@PathVariable int id) {
        accountService.enableAccount(id);
        return ResponseEntity.ok(new DeleteResponse(" đã mở account"));
    }

//    @PutMapping("/{id}/disable")
//    public ResponseEntity<?> disableAccount(@PathVariable Long id) {
//        accountService.disableAccount(id);
//        return ResponseEntity.ok().build();
//    }


}