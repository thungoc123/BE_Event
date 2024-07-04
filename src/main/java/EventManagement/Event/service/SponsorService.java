package EventManagement.Event.service;

import EventManagement.Event.DTO.SponsorDTO;
import EventManagement.Event.DTO.SponsorRegistrationDto;
import EventManagement.Event.entity.*;
import EventManagement.Event.mapper.SponsorMapper;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;
import EventManagement.Event.repository.*;
import EventManagement.Event.service.imp.SponsorProgramImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private SponsorEventRepository sponsorEventRepository;

    public List<SponsorProgram> getAllSponsorPrograms() {
        return sponsorProgramRepository.findAll();
    }

    public List<SponsorProgram> getProgramsByAccountId(HttpServletRequest request) {
        String accountId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return sponsorProgramRepository.findByAccountId(Integer.parseInt(accountId));


    }

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
        try {
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
            try {
                sponsorProgram.setAccount(account);
            } catch (Exception e) {
                System.out.println("Error setting account: " + e.getMessage());
            }

            try {
                sponsorProgram.setTitle(insertSponsorProgramRequest.getTitle());
            } catch (Exception e) {
                System.out.println("Error setting title: " + e.getMessage());
            }

            try {
                sponsorProgram.setLink(insertSponsorProgramRequest.getWebsiteLink());
            } catch (Exception e) {
                System.out.println("Error setting link: " + e.getMessage());
            }
            try {
                sponsorProgram.setDescription(insertSponsorProgramRequest.getDescription());
            } catch (Exception e) {
                System.out.println("Error setting description: " + e.getMessage());
            }
            try {
                sponsorProgram.setThumbnail(insertSponsorProgramRequest.getThumbnail());
            } catch (Exception e) {
                System.out.println("Error setting thumbnail: " + e.getMessage());
            }
            try {
                sponsorProgram.setLocation(insertSponsorProgramRequest.getLocation());
            } catch (Exception e) {
                System.out.println("Error setting location: " + e.getMessage());
            }


            try {
                SponsorProgram.State state = SponsorProgram.State.valueOf(insertSponsorProgramRequest.getState().toUpperCase());
                sponsorProgram.setState(state);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid state value: " + insertSponsorProgramRequest.getState());
            }
            SponsorProgram programSave = sponsorProgramRepository.save(sponsorProgram);
            return programSave != null;

        }  catch(Exception e){
                e.printStackTrace();
                return false;
        }
    }
    @Override
    public boolean addEventsToSponsorProgram(int sponsorProgramId, List<Integer> eventIds) {
        try {
            SponsorProgram sponsorProgram = sponsorProgramRepository.findById(sponsorProgramId)
                    .orElseThrow(() -> new RuntimeException("Sponsor program not found"));

            List<Event> events = new ArrayList<>();
            for (Integer eventId : eventIds) {
                Event event = eventRepository.findById(eventId).orElse(null);
                if (event == null) {
                    throw new RuntimeException("Event not found");
                }
                events.add(event);
            }

            sponsorProgram.setEvents(new HashSet<>(events));
            sponsorProgramRepository.save(sponsorProgram);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insertSponsor(InsertSponsorRequest insertSponsorRequest) {

        try {
            int eventId = insertSponsorRequest.getEventId();
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + eventId));

            Long sponsorId = insertSponsorRequest.getSponsorId();
            Sponsor sponsor = sponsorRepository.findById(sponsorId).orElse(null);
            if (sponsor == null) {
                throw new RuntimeException("Account not found");
            }
            SponsorEvent sponsorEvent = new SponsorEvent();
            sponsorEvent.setEvent(event);
            sponsorEvent.setSponsor(sponsor);
            sponsorEvent.setProfitPercent(insertSponsorRequest.getProfitPercentage());
            sponsorEventRepository.save(sponsorEvent);

            return true;
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean updateProgram(int sponsorProgramId, InsertSponsorProgramRequest insertSponsorProgramRequest){
        try{
            SponsorProgram sponsorProgram = sponsorProgramRepository.findById(sponsorProgramId).orElse(null);
            if(sponsorProgram == null){
                throw new RuntimeException("Sponsor program not found");
            }
            sponsorProgram.setTitle(insertSponsorProgramRequest.getTitle());
            sponsorProgram.setDescription(insertSponsorProgramRequest.getDescription());
            sponsorProgram.setThumbnail(insertSponsorProgramRequest.getThumbnail());
            sponsorProgram.setLocation(insertSponsorProgramRequest.getLocation());
            sponsorProgram.setLink(insertSponsorProgramRequest.getWebsiteLink());
            sponsorProgramRepository.save(sponsorProgram);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public boolean removeEventFromSponsorProgram(int sponsorProgramId, int eventId){
        try{
            SponsorProgram sponsorProgram = sponsorProgramRepository.findById(sponsorProgramId).orElse(null);
            if(sponsorProgram == null){
                throw new RuntimeException("Sponsor program not found");
            }
            Event event = eventRepository.findById(eventId).orElse(null);
            if(event == null){
                throw new RuntimeException("Event not found");
            }
            eventRepository.deleteSponsorProgramEventByEventId(eventId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteProgram(int sponsorProgramId){
        try{
            SponsorProgram sponsorProgram = sponsorProgramRepository.findById(sponsorProgramId).orElse(null);
            if(sponsorProgram == null){
                throw new RuntimeException("Sponsor program not found");
            }
            sponsorProgramRepository.deleteSponsorProgramEventBySponsorProgramId(sponsorProgramId);
            sponsorProgramRepository.delete(sponsorProgram);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}
