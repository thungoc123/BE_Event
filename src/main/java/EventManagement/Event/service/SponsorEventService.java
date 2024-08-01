package EventManagement.Event.service;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.SponsorEvent;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.SponsorEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SponsorEventService {
    @Autowired
    private SponsorEventRepository sponsorEventRepository;

    @Autowired
    private EventRepository eventRepository;

    public Map<String, Object>  getTotalContributedCapitalPercentage(int eventId) {
        List<SponsorEvent> sponsorEvents = sponsorEventRepository.findByEventId(eventId);

        int totalContributedCapital = sponsorEvents.stream()
                .map(SponsorEvent::getContributedCapital)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
        Optional<Event> events = eventRepository.findById(eventId);
        if(events.isPresent()) {
            Event event = events.get();
            Integer fundraising = event.getFundraising();
            if (fundraising == null) {
                throw new IllegalArgumentException("The event has not started fundraising.");
            }
            double percentage;
            if (totalContributedCapital == 0) {
                percentage = 0;
            }
            percentage = (totalContributedCapital / (0.7 * fundraising))*100 ;
            Map<String, Object> result = new HashMap<>();
            result.put("fundraising", fundraising);
            result.put("percentage", percentage);

            return result;
        } else {
            throw new NoSuchElementException("Event not found");
        }
    }
}
