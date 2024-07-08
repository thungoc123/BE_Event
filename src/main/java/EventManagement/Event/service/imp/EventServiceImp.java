package EventManagement.Event.service.imp;

import EventManagement.Event.entity.Event;
import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;

import java.util.List;

public interface EventServiceImp {
    boolean insertEvent(InsertEventRequest request);
    boolean updateEvent(int EventId,  InsertEventRequest request);
    boolean deleteEvent(int EventId);
    boolean changeStateEvent(int eventId);


}

