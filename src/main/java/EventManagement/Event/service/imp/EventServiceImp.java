package EventManagement.Event.service.imp;

import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;

public interface EventServiceImp {
    boolean insertEvent(InsertEventRequest request);
    boolean insertSponsor(InsertSponsorRequest insertSponsorRequest);

}

