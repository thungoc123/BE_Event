package EventManagement.Event.service.imp;

import EventManagement.Event.payload.Request.InsertEventRequest;

public interface EventServiceImp {
    Boolean insertEvent(InsertEventRequest request);
}

