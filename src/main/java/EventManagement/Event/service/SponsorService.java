package EventManagement.Event.service;

import EventManagement.Event.DTO.SponsorRegistrationDto;
import EventManagement.Event.entity.Account;
import EventManagement.Event.entity.Role;
import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.repository.RoleRepository;
import EventManagement.Event.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SponsorService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void registerSponsor(SponsorRegistrationDto sponsorDto) throws Exception {
        if (accountRepository.existsByEmail(sponsorDto.getEmail())) {
            throw new Exception("Email already exists");
        }

        if (!sponsorDto.getPassword().equals(sponsorDto.getPassword())) {
            throw new Exception("Passwords do not match");
        }

        Role role = roleRepository.findByRoleName("ROLE_SPONSOR");
        if (role == null) {
            role = new Role();
            role.setRoleName("ROLE_SPONSOR");
            role = roleRepository.save(role);
        }

        Account account = new Account();
        account.setEmail(sponsorDto.getEmail());
        account.setPassword(sponsorDto.getPassword());
        account.setRole(role);
        accountRepository.save(account);

        Sponsor sponsor = new Sponsor();
        sponsor.setCompanyName(sponsorDto.getCompanyName());
        sponsor.setCompanyID(sponsorDto.getCompanyID());
        sponsor.setFptStaffEmail(sponsorDto.getFptStaffEmail());
        sponsor.setInformation(sponsorDto.getInformation());
        sponsor.setAccount(account);
        sponsorRepository.save(sponsor);


    }
}
