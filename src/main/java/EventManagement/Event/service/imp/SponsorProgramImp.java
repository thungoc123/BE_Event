package EventManagement.Event.service.imp;

import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;

import java.util.List;

public interface SponsorProgramImp {
    boolean insertSponsorProgram(InsertSponsorProgramRequest insertSponsorProgramRequest);
    boolean insertSponsor(InsertSponsorRequest insertSponsorRequest);
    boolean addEventsToSponsorProgram(int sponsorProgramId, List<Integer> eventIds);
    boolean updateProgram(int sponsorProgramId, InsertSponsorProgramRequest insertSponsorProgramRequest);
    boolean removeEventFromSponsorProgram(int sponsorProgramId, int eventId);
    boolean deleteProgram(int sponsorProgramId);
}
