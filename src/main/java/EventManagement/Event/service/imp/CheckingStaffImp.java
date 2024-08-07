package EventManagement.Event.service.imp;

import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.payload.Request.InsertCheckingStaffRequest;

public interface CheckingStaffImp {
    boolean insertCheckingStaff(InsertCheckingStaffRequest insertCheckingStaffRequest );
    boolean deleteCheckingStaff(int checkingStaffId, int eventId);
    boolean deleteAllCheckingStaff(int eventId);
//    void sendMailService(String email, String subject, String body);
}
