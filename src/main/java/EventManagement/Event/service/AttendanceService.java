package EventManagement.Event.service;

import EventManagement.Event.DTO.AttendanceRequestDTO;
import EventManagement.Event.entity.Attendance;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.repository.AttendanceRepository;
import EventManagement.Event.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final TicketRepository ticketRepository;

    public void createAttendanceList(int eventId, List<AttendanceRequestDTO> attendanceRequests) {
        for (AttendanceRequestDTO request : attendanceRequests) {
            log.info("Processing ticket ID: {}", request.getTicketId());
            Ticket ticket = ticketRepository.findById(request.getTicketId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Ticket ID: " + request.getTicketId()));

            if (ticket.getEvent().getId() != eventId) {
                throw new IllegalArgumentException("Ticket ID " + request.getTicketId() + " does not belong to the specified Event ID: " + eventId);
            }

            Attendance attendance = new Attendance();
            attendance.setTicket(ticket);
            attendance.setStatus(Attendance.AttendanceStatus.ABSENT); // Hardcoded to ABSENT

            attendanceRepository.save(attendance);
            log.info("Saved attendance for ticket ID: {}", request.getTicketId());
        }
    }
}
