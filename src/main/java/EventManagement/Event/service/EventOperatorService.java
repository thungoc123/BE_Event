package EventManagement.Event.service;

import EventManagement.Event.DTO.EventOperatorDTO;
import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.EventOperator;
import EventManagement.Event.entity.Role;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.EventOperatorRepository;
import EventManagement.Event.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventOperatorService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EventOperatorRepository eventOperatorRepository;
    public void signupEventOperator(EventOperatorDTO eventOperatorDTO) throws Exception {
        if (accountRepository.existsByEmail(eventOperatorDTO.getEmail())) {
            throw new Exception("Email already exists");
        }

        if (!eventOperatorDTO.getPassword().equals(eventOperatorDTO.getPassword())) {
            throw new Exception("Passwords do not match");
        }
        Role role = roleRepository.findByRoleName("ROLE_EO");
        if (role == null) {
            role = new Role();
            role.setRoleName("ROLE_EO");
            role = roleRepository.save(role);
        }
        Account account = new Account();
        account.setEmail(eventOperatorDTO.getEmail());
        account.setPassword(eventOperatorDTO.getPassword());
        account.setRole(role);
        accountRepository.save(account);

        // Create an EventOperator entity
        EventOperator eventOperator = new EventOperator();
        eventOperator.setInformation(eventOperatorDTO.getInformation());
        eventOperator.setAccount(account);

        // Save the Account (and cascade save EventOperator)
        eventOperatorRepository.save(eventOperator);
    }
}
