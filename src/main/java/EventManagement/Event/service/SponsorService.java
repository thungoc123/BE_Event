package EventManagement.Event.service;

import EventManagement.Event.DTO.SponsorDTO;
import EventManagement.Event.DTO.SponsorProfitDTO;
import EventManagement.Event.DTO.SponsorRegistrationDto;
import EventManagement.Event.entity.*;
import EventManagement.Event.entity.SponsorProgramEvent;
import EventManagement.Event.mapper.SponsorMapper;
import EventManagement.Event.payload.Request.AddEventsToSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;
import EventManagement.Event.repository.*;
import EventManagement.Event.service.imp.SponsorProgramImp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SponsorService implements SponsorProgramImp {

    @Autowired
    private PasswordEncoder passwordEncoder;
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
    @Autowired SponsorProgramEventRepository sponsorProgramEventRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EventProfitRepository eventProfitRepository;

    public List<SponsorProgram> getAllSponsorPrograms() {
        List<SponsorProgram> sponsorPrograms = sponsorProgramRepository.findAll();
        return sponsorPrograms;
//        return sponsorProgramRepository.findAll();
    }

    public List<SponsorProgram> getProgramsByAccountId(HttpServletRequest request) {
        String accountId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return sponsorProgramRepository.findByAccountId(Integer.parseInt(accountId));


    }

    public List<Sponsor> getSponsorsByAccountId(int accountId) {
        List<Sponsor> sponsors = sponsorRepository.findByAccountId(accountId);
        return sponsors;
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
        account.setPassword(passwordEncoder.encode(sponsorDto.getPassword()));
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
            sponsorProgramRepository.save(sponsorProgram);
            return true;

        }  catch(Exception e){
                e.printStackTrace();
                return false;
        }
    }
    @Override
    public boolean addEventsToSponsorProgram(AddEventsToSponsorProgramRequest addEventsToSponsorProgramRequest) {
        try {
            int sponsorProgramId = addEventsToSponsorProgramRequest.getSponsorProgramId();
            SponsorProgram sponsorProgram = sponsorProgramRepository.findById(sponsorProgramId)
                    .orElseThrow(() -> new RuntimeException("Sponsor program not found"));

            List<Integer> eventIds = addEventsToSponsorProgramRequest.getEventIds();
            List<Event> events = eventRepository.findAllById(eventIds);

            if (events.size() != eventIds.size()) {
                throw new RuntimeException("One or more events not found");
            }

            for (Event event : events) {
                SponsorProgramEvent sponsorProgramEvent = new SponsorProgramEvent();

                sponsorProgramEvent.setEvent(event);
                sponsorProgramEvent.setSponsorProgram(sponsorProgram);

                sponsorProgramEventRepository.save(sponsorProgramEvent);

            }



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

    @Override
    public boolean deleteSponsor(int eventId, Long sponsorId) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + eventId));

            Sponsor sponsor = sponsorRepository.findById(sponsorId)
                    .orElseThrow(() -> new NoSuchElementException("Sponsor not found for ID: " + sponsorId));

            List<SponsorEvent> sponsorEvents = sponsorEventRepository.findByEventAndSponsor(event, sponsor);
            if (sponsorEvents.isEmpty()) {
                throw new NoSuchElementException("SponsorEvent not found for given event and sponsor");
            }

            for (SponsorEvent sponsorEvent : sponsorEvents) {
                sponsorEventRepository.delete(sponsorEvent);
            }

            return true;
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
            return false;
        }
    }
    public List<SponsorProfitDTO> getSponsorProfitsByEventId(int eventId, Double totalEventProfit) {
        List<SponsorProfitDTO> sponsorProfits = new ArrayList<>();

        List<SponsorEvent> sponsorEvents = getSponsorEventsByEventId(eventId);
        for (SponsorEvent sponsorEvent : sponsorEvents) {
            // Tính sponsorProfitPercent dựa trên tổng lợi nhuận
            Double sponsorProfitPercent = (sponsorEvent.getProfitPercent() / 100.0);

            // Tính profitAmount dựa trên sponsorProfitPercent và totalEventProfit
            Double profitAmount = sponsorProfitPercent * totalEventProfit;

            SponsorProfitDTO dto = new SponsorProfitDTO();
            dto.setSponsorId(sponsorEvent.getSponsor().getId());
            dto.setCompanyName(sponsorEvent.getSponsor().getCompanyName());
            dto.setSponsorEmail(sponsorEvent.getSponsor().getFptStaffEmail());
            dto.setSponsorProfitPercent(sponsorProfitPercent * 100); // Đảm bảo hiển thị phần trăm
            dto.setProfitAmount(profitAmount);

            sponsorProfits.add(dto);
        }

        return sponsorProfits;
    }



    public Double getTotalEventProfit(int eventId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<SponsorEvent> root = cq.from(SponsorEvent.class);

        cq.select(cb.sum(root.get("profitPercent")))
                .where(cb.equal(root.get("event").get("id"), eventId));

        TypedQuery<Double> query = entityManager.createQuery(cq);
        Double totalProfit = query.getSingleResult();
        return totalProfit != null ? totalProfit : 0.0; // Đảm bảo trả về 0 nếu không có kết quả
    }

    private List<SponsorEvent> getSponsorEventsByEventId(int eventId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SponsorEvent> cq = cb.createQuery(SponsorEvent.class);
        Root<SponsorEvent> root = cq.from(SponsorEvent.class);

        cq.select(root)
                .where(cb.equal(root.get("event").get("id"), eventId));

        TypedQuery<SponsorEvent> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    public List<SponsorProfitDTO> getSponsorProfitsByEventIdAndAccountId(int eventId, int accountId, Double totalEventProfit) {
        List<SponsorProfitDTO> sponsorProfits = new ArrayList<>();

        List<SponsorEvent> sponsorEvents = getSponsorEventsByEventIdAndAccountId(eventId, accountId);
        for (SponsorEvent sponsorEvent : sponsorEvents) {
            // Tính sponsorProfitPercent dựa trên tổng lợi nhuận
            Double sponsorProfitPercent = (sponsorEvent.getProfitPercent() / 100.0);

            // Tính profitAmount dựa trên sponsorProfitPercent và totalEventProfit
            Double profitAmount = sponsorProfitPercent * totalEventProfit;

            SponsorProfitDTO dto = new SponsorProfitDTO();
            dto.setSponsorId(sponsorEvent.getSponsor().getId());
            dto.setCompanyName(sponsorEvent.getSponsor().getCompanyName());
            dto.setSponsorEmail(sponsorEvent.getSponsor().getAccount().getEmail());
            dto.setSponsorProfitPercent(sponsorProfitPercent * 100); // Đảm bảo hiển thị phần trăm
            dto.setProfitAmount(profitAmount);

            sponsorProfits.add(dto);
        }

        return sponsorProfits;
    }


    private List<SponsorEvent> getSponsorEventsByEventIdAndAccountId(int eventId, int accountId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SponsorEvent> cq = cb.createQuery(SponsorEvent.class);
        Root<SponsorEvent> root = cq.from(SponsorEvent.class);

        cq.select(root)
                .where(cb.equal(root.get("event").get("id"), eventId),
                        cb.equal(root.get("sponsor").get("account").get("id"), accountId));

        TypedQuery<SponsorEvent> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
    public List<SponsorProfitDTO> getSponsorProfitsByAccountId(int accountId) {
        List<SponsorProfitDTO> sponsorProfits = new ArrayList<>();

        List<SponsorEvent> sponsorEvents = getSponsorEventsByAccountId(accountId);
        for (SponsorEvent sponsorEvent : sponsorEvents) {
            // Gọi hàm tính tổng lợi nhuận của sự kiện
            Double totalEventProfit = calculateTotalEventProfit(sponsorEvent.getEvent().getId());

            // Tính sponsorProfitPercent dựa trên tổng lợi nhuận
            Double sponsorProfitPercent = (sponsorEvent.getProfitPercent() / 100.0);

            // Tính profitAmount dựa trên sponsorProfitPercent và totalEventProfit
            Double profitAmount = sponsorProfitPercent * (totalEventProfit*70/100);

            SponsorProfitDTO dto = new SponsorProfitDTO();
            dto.setSponsorId(sponsorEvent.getSponsor().getId());
            dto.setCompanyName(sponsorEvent.getSponsor().getCompanyName());
            dto.setSponsorEmail(sponsorEvent.getSponsor().getAccount().getEmail());
            dto.setSponsorProfitPercent(sponsorProfitPercent * 100); // Đảm bảo hiển thị phần trăm
            dto.setEventName(sponsorEvent.getEvent().getName());
            dto.setProfitAmount(profitAmount);

            sponsorProfits.add(dto);
        }

        return sponsorProfits;
    }

    private List<SponsorEvent> getSponsorEventsByAccountId(int accountId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SponsorEvent> cq = cb.createQuery(SponsorEvent.class);
        Root<SponsorEvent> root = cq.from(SponsorEvent.class);

        cq.select(root)
                .where(cb.equal(root.get("sponsor").get("account").get("id"), accountId));

        TypedQuery<SponsorEvent> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    // Giả sử đây là hàm tính tổng lợi nhuận của sự kiện
    private Double calculateTotalEventProfit(int eventId) {
        return eventProfitRepository.findByEventId(eventId)
                .map(EventProfit::getTotalProfit)
                .orElse(0.0);  // Hoặc trả về giá trị mặc định khác nếu không tìm thấy
    }

}
