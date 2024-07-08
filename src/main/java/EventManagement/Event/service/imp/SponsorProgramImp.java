package EventManagement.Event.service.imp;

import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.payload.Request.AddEventsToSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;

import java.util.List;

public interface SponsorProgramImp {
    boolean insertSponsorProgram(InsertSponsorProgramRequest insertSponsorProgramRequest);
    boolean insertSponsor(InsertSponsorRequest insertSponsorRequest);
    boolean addEventsToSponsorProgram(AddEventsToSponsorProgramRequest addEventsToSponsorProgramRequest);
    boolean updateProgram(int sponsorProgramId, InsertSponsorProgramRequest insertSponsorProgramRequest);
    boolean removeEventFromSponsorProgram(int sponsorProgramId, int eventId);
    boolean deleteProgram(int sponsorProgramId);
    boolean deleteSponsor(int eventId, Long sponsorId);
}
