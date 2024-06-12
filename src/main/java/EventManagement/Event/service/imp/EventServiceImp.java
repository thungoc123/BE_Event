package EventManagement.Event.service.imp;

import EventManagement.Event.payload.Request.InsertEventRequest;

public interface EventServiceImp {
    boolean insertEvent(InsertEventRequest request);
}

