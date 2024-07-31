package EventManagement.Event.service;

import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.SponsorEvent;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.SponsorEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SponsorEventService {
    @Autowired
    private SponsorEventRepository sponsorEventRepository;

    @Autowired
    private EventRepository eventRepository;

    public double getTotalContributedCapitalPercentage(int eventId) {
        List<SponsorEvent> sponsorEvents = sponsorEventRepository.findByEventId(eventId);
        int totalContributedCapital = sponsorEvents.stream()
                .mapToInt(SponsorEvent::getContributedCapital)
                .sum();
        Optional<Event> events = eventRepository.findById(eventId);
        if(events.isPresent()) {
            Event event = events.get();
            double fundraising = event.getFundraising();
            double percentage = (totalContributedCapital / (0.7 * fundraising))*100 ;
            return percentage;
        } else {
            throw new NoSuchElementException("Event not found");
        }
    }
}
