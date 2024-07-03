package EventManagement.Event.service.imp;

import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;

import java.util.List;

public interface SponsorProgramImp {
    boolean insertSponsorProgram(InsertSponsorProgramRequest insertSponsorProgramRequest);
    boolean insertSponsor(InsertSponsorRequest insertSponsorRequest);
    boolean addEventsToSponsorProgram(int sponsorProgramId, List<Integer> eventIds);
//    boolean deleteSponsor(int eventId);
}
