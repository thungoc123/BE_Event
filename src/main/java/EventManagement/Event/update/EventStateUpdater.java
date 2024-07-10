package EventManagement.Event.update;

import EventManagement.Event.entity.Event;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.StateEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EventStateUpdater {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private StateEventRepository stateEventRepository;

    @Scheduled(fixedDelay = 60000)
    public void updateEventStates() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current DateTime: " + currentDateTime);
        List<Event> events = eventRepository.findByTimeendBefore(currentDateTime);
        System.out.println("Found " + events.size() + " events with timeend <= currentDateTime");

        // Lấy các sự kiện đã qua thời gian kết thúc

        for (Event event : events) {
            System.out.println("Event ID: " + event.getId() + ", timeend: " + event.getTimeend());
            event.setStateEvent(stateEventRepository.findById(3)); // Chuyển state sang 3
            System.out.println("Updating event " + event.getId());
        }



        eventRepository.saveAll(events);
        System.out.println("Saved " + events.size() + " events successfully.");// Lưu các sự kiện đã cập nhật
    }

}
