package EventManagement.Event.service;

import EventManagement.Event.DTO.EventOperatorDTO;
import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.EventOperator;
import EventManagement.Event.entity.Role;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.EventOperatorRepository;
import EventManagement.Event.repository.RoleRepository;
import jakarta.validation.constraints.Email;
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
    @Autowired
    private EmailService emailService;
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
        account.setEnabled(false); // Tạo tài khoản luôn bị vô hiệu hóa
        accountRepository.save(account);

        // Tạo một đối tượng EventOperator
        EventOperator eventOperator = new EventOperator();
        eventOperator.setInformation(eventOperatorDTO.getInformation());
        eventOperator.setAccount(account);

        // Lưu đối tượng EventOperator (cascade save Account)
        eventOperatorRepository.save(eventOperator);

        String subject = "Create Event Operator successful";
        String text = "Hi guy,\n\nYour Event Operator Account has been successfully created.\n\nEmail: " + account.getEmail() + "\nPassword: " + account.getPassword() + "\n\nBest regards";
        text += "\n\nCongratulations! You are now a staff member for the following events:\n";

        emailService.sendEmail(account.getEmail(), subject, text);
    }
}
