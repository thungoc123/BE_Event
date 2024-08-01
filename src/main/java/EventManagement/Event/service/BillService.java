package EventManagement.Event.service;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.entity.SponsorEvent;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.SponsorEventRepository;
import EventManagement.Event.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private SponsorEventRepository sponsorEventRepository;

    public List<Map<String, Object>> getBill(int eventId, Long sponsorId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + eventId));

        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new NoSuchElementException("Sponsor not found for ID: " + sponsorId));
        List<SponsorEvent> sponsorEvents = sponsorEventRepository.findByEventAndSponsor(event, sponsor);
        if (sponsorEvents.isEmpty()) {
            throw new NoSuchElementException("SponsorEvent not found for given event and sponsor");
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (SponsorEvent sponsorEvent : sponsorEvents) {
            Map<String, Object> data = new HashMap<>();
            data.put("eventName", sponsorEvent.getEvent().getName());
            data.put("contributedCapital", sponsorEvent.getContributedCapital());
            result.add(data);
        }

        return result;
    }
}
