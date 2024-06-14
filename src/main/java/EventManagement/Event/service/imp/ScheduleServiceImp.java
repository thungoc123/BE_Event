package EventManagement.Event.service.imp;

import EventManagement.Event.payload.Request.InsertScheduleRequest;

public interface ScheduleServiceImp {
    boolean insertSchedule(InsertScheduleRequest insertScheduleRequest);
}
