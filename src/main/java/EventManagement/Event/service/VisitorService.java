package EventManagement.Event.service;

import EventManagement.Event.DTO.VisitorRegistrationDTO;
import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Role;

import EventManagement.Event.entity.Visitor;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.RoleRepository;

import EventManagement.Event.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private RoleRepository roleRepository;


    public void signUpVisitor(VisitorRegistrationDTO visitorRegistrationDTO) throws Exception {
        // Kiểm tra xem email đã tồn tại trong hệ thống chưa
        if (accountRepository.existsByEmail(visitorRegistrationDTO.getEmail())) {
            throw new Exception("Email already exists");
        }

        // Kiểm tra xem mật khẩu và xác nhận mật khẩu có trùng khớp không
        if (!visitorRegistrationDTO.getPassword().equals(visitorRegistrationDTO.getConfirmPassword())) {
            throw new Exception("Passwords do not match");
        }

        // Tìm role "VISITOR" hoặc tạo mới nếu chưa tồn tại
        Role role = roleRepository.findByRoleName("ROLE_VISITOR");
        if (role == null) {
            role = new Role();
            role.setRoleName("ROLE_VISITOR");
            role = roleRepository.save(role);
        }

        // Tạo mới tài khoản
        Account account = new Account();
        account.setEmail(visitorRegistrationDTO.getEmail());
        account.setPassword(visitorRegistrationDTO.getPassword());
        account.setRole(role);
        accountRepository.save(account);

        // Tạo mới visitor và liên kết với tài khoản vừa tạo
        Visitor visitor = new Visitor();
        visitor.setInformation(visitorRegistrationDTO.getInformation());
        visitor.setAccount(account);
        visitorRepository.save(visitor);
    }

    public Optional<Visitor> findById(int id) {
        return visitorRepository.findById(id);
    }

    public List<Visitor> findByAccountId(int accountId) {
        return visitorRepository.findByAccountId(accountId);
    }
}


