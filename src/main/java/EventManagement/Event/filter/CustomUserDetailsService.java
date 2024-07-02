//package EventManagement.Event.filter;
//
//import EventManagement.Event.entity.Account;
//import EventManagement.Event.repository.AccountRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Account account = accountRepository.findByEmail(email);
//        if (account == null || !account.isEnabled()) {
//            throw new UsernameNotFoundException("User not found or account is disabled");
//        }
//        return new User(account.getEmail(), account.getPassword(), Collections.emptyList());
//    }
//}
//
