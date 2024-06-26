package EventManagement.Event.service;

import EventManagement.Event.DTO.SponsorDTO;
import EventManagement.Event.DTO.SponsorRegistrationDto;
import EventManagement.Event.entity.*;
import EventManagement.Event.mapper.SponsorMapper;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;
import EventManagement.Event.repository.*;
import EventManagement.Event.service.imp.SponsorProgramImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SponsorService implements SponsorProgramImp {
    @Autowired
    private SponsorProgramRepository sponsorProgramRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EventRepository eventRepository;
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

    public List<SponsorDTO> getAllSponsors() {
        List<Sponsor> sponsors = sponsorRepository.findAll();
        return sponsors.stream()
                .map(SponsorMapper::toSponsorDto)
                .collect(Collectors.toList());
    }

    public Optional<SponsorDTO> getSponsorById(Long id) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.findById(id);
        if (sponsorOptional.isPresent()) {
            Sponsor sponsor = sponsorOptional.get();
            return Optional.of(SponsorMapper.toSponsorDto(sponsor));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean insertSponsorProgram(InsertSponsorProgramRequest insertSponsorProgramRequest) {
        if (insertSponsorProgramRequest.getTitle() == null || insertSponsorProgramRequest.getTitle().isEmpty()) {
            throw new RuntimeException("Sponsor program title cannot be empty");
        } // check name ko co
        if (sponsorProgramRepository.existsByTitle(insertSponsorProgramRequest.getTitle())) {
            throw new RuntimeException("Sponsor program with title '" + insertSponsorProgramRequest.getTitle() + "' already exists");
        } // check title trung

            int accountId = insertSponsorProgramRequest.getAccountId();

            Account account = accountRepository.findById(accountId);
            if (account == null) {
                throw new RuntimeException("Can't find accountId: " + insertSponsorProgramRequest.getAccountId());
            }
            SponsorProgram sponsorProgram = new SponsorProgram();
            sponsorProgram.setAccount(account);
            sponsorProgram.setTitle(insertSponsorProgramRequest.getTitle());
            sponsorProgram.setLink(insertSponsorProgramRequest.getWebsiteLink());
            sponsorProgram.setDescription(insertSponsorProgramRequest.getDescription());
            sponsorProgram.setThumbnail(insertSponsorProgramRequest.getThumbnail());
            sponsorProgram.setLocation(insertSponsorProgramRequest.getLocation());


            try {
                SponsorProgram.State state = SponsorProgram.State.valueOf(insertSponsorProgramRequest.getState().toUpperCase());
                sponsorProgram.setState(state);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid state value: " + insertSponsorProgramRequest.getState());
            }
            List<Event> events = new ArrayList<>();
            for (Integer eventId : insertSponsorProgramRequest.getEventIds()) {
                Event event = eventRepository.findById(eventId).orElse(null);
                if (event == null) {
                    throw new RuntimeException("Event not found");
                }
                events.add(event);


            }
            sponsorProgram.setEvents(new HashSet<>(events));
            sponsorProgramRepository.save(sponsorProgram);
            return true;
        }


    @Override
    public boolean insertSponsor(InsertSponsorRequest insertSponsorRequest) {

        try {
            int eventId = insertSponsorRequest.getEventId();
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + eventId));

            String email = insertSponsorRequest.getEmail();
            Sponsor sponsor = sponsorRepository.findByfptStaffEmail(email);
            if (sponsor == null) {
                throw new RuntimeException("Account not found");
            }
            if (event.getSponsor() != null) {
                throw new RuntimeException("Event already has a sponsor");
            }

            event.setSponsor(sponsor);
            eventRepository.save(event);

            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean deleteSponsor(int eventId, int sponsorId, int accountId){
        try {

            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + eventId));


            if (event.getAccount().getId() != accountId) {
                throw new RuntimeException("Event does not belong to accountId: " + accountId);
            }


            if (event.getSponsor() == null) {
                throw new RuntimeException("Event does not have a sponsor");
            }


            event.setSponsor(null);
            eventRepository.save(event);

            System.out.println("Sponsor removed successfully from event with ID " + eventId);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
            return false;
        }

    }
}
