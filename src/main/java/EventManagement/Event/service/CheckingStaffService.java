package EventManagement.Event.service;

import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Role;
import EventManagement.Event.payload.Request.InsertCheckingStaffRequest;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.CheckingStaffRepository;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.RoleRepository;
import EventManagement.Event.service.imp.CheckingStaffImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CheckingStaffService implements CheckingStaffImp {

    @Autowired
    private EmailService emailService;
    @Autowired
    private CheckingStaffRepository checkingStaffRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean insertCheckingStaff(InsertCheckingStaffRequest insertCheckingStaffRequest) {

        int eventId = insertCheckingStaffRequest.getEventId();
        Event event = eventRepository.findById(eventId).orElse(null);
        System.out.println("Fetching event with ID: " + eventId);
        String randomPassword = generateRandomPassword(8);
        if(event == null){
            System.out.println("Event with ID " + eventId + " not found.");
            return false;
        }

        Role role = roleRepository.findByRoleName("ROLE_CHECKING_STAFF");
        if (role == null) {
            System.out.println("Role ROLE_CHECKING_STAFF not found, creating new role.");
            role = new Role();
            role.setRoleName("ROLE_CHECKING_STAFF");
            role = roleRepository.save(role);
        }
        else {
            System.out.println("Role found: " + role.getRoleName());
        }
        String email = insertCheckingStaffRequest.getEmail();
        Account account =  new Account();
        account.setEmail(email);
        account.setPassword(randomPassword);
        account.setRole(role);
        Account accountSaved = accountRepository.save(account);
        System.out.println("Account created with ID: " + accountSaved.getId());

        CheckingStaff checkingStaff = new CheckingStaff();
        checkingStaff.setAccount(accountSaved);
        checkingStaff.setEvent(event);
        checkingStaffRepository.save(checkingStaff);

        return true;


    }
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }


}
