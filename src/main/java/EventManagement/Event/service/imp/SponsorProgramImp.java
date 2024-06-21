package EventManagement.Event.service.imp;

import EventManagement.Event.entity.SponsorProgram;
import EventManagement.Event.payload.Request.InsertSponsorProgramRequest;

public interface SponsorProgramImp {
    boolean insertSponsorProgram(InsertSponsorProgramRequest insertSponsorProgramRequest);
}
