package EventManagement.Event.service.imp;

import EventManagement.Event.payload.Request.InsertEventRequest;
import EventManagement.Event.payload.Request.InsertScheduleRequest;

public interface ScheduleServiceImp {
    boolean insertSchedule(InsertScheduleRequest insertScheduleRequest);
    boolean updateSchedule(int eventId,int accountId,int scheduleId,  InsertScheduleRequest insertScheduleRequest);
}
