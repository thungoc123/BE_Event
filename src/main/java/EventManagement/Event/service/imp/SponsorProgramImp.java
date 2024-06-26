package EventManagement.Event.service.imp;

import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;
import EventManagement.Event.payload.Request.InsertSponsorRequest;

public interface SponsorProgramImp {
    boolean insertSponsorProgram(InsertSponsorProgramRequest insertSponsorProgramRequest);
    boolean insertSponsor(InsertSponsorRequest insertSponsorRequest);
    boolean deleteSponsor(int eventId, int sponsorId, int accountId);
}
