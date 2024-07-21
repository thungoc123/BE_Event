package EventManagement.Event.service;

import EventManagement.Event.DTO.AttendanceDTO;
import EventManagement.Event.DTO.TicketDTO;
import EventManagement.Event.DTO.VisitorDTO;
import EventManagement.Event.entity.Attendance;
import EventManagement.Event.entity.CheckingStaff;
import EventManagement.Event.entity.Ticket;
import EventManagement.Event.repository.AttendanceRepository;
import EventManagement.Event.repository.CheckingStaffRepository;
import EventManagement.Event.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CheckingStaffRepository checkingStaffRepository;

    public List<Attendance> createAttendancesForEvent(int eventId) {
        try {
            List<Ticket> tickets = ticketRepository.findByEvent_Id(eventId);

            // Filter tickets to only include those that are PAID and do not have an existing attendance
            List<Ticket> validTickets = tickets.stream()
                    .filter(ticket -> ticket.getStatus() == Ticket.Status.PAID && attendanceRepository.findByTicket_Id(ticket.getId()).isEmpty())
                    .toList(); // Using toList() instead of collect(Collectors.toList())

            List<Attendance> attendances = validTickets.stream().map(ticket -> {
                Attendance attendance = new Attendance();
                attendance.setTicket(ticket);
                attendance.setEventId(eventId);
                attendance.setStatus(Attendance.AttendanceStatus.ABSENT);
                return attendance;
            }).toList(); // Using toList() instead of collect(Collectors.toList())

            return attendanceRepository.saveAll(attendances);
        } catch (Exception e) {
            log.error("Error creating attendances for event ID: {}", eventId, e);
            throw new RuntimeException("Error creating attendances for event ID: " + eventId);
        }
    }

    public Attendance updateAttendanceStatus(int ticketId, Attendance.AttendanceStatus status) {
        try {
            List<Attendance> attendances = attendanceRepository.findAllByTicket_Id(ticketId);
            if (attendances.size() == 1) {
                Attendance attendance = attendances.get(0);
                attendance.setStatus(status);
                return attendanceRepository.save(attendance);
            } else if (attendances.isEmpty()) {
                log.warn("Invalid Ticket ID: {}", ticketId);
                throw new IllegalArgumentException("Invalid Ticket ID: " + ticketId);
            } else {
                log.warn("Multiple Attendance records found for Ticket ID: {}", ticketId);
                throw new IllegalStateException("Multiple Attendance records found for Ticket ID: " + ticketId);
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Error updating attendance status for ticket ID: {}", ticketId, e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error updating attendance status for ticket ID: {}", ticketId, e);
            throw new RuntimeException("Unexpected error updating attendance status for ticket ID: " + ticketId);
        }
    }

    public List<Attendance> getAllAttendances() {
        try {
            return attendanceRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching all attendances", e);
            throw new RuntimeException("Error fetching all attendances");
        }
    }

    public List<Attendance> getAttendancesByEventId(int eventId) {
        try {
            return attendanceRepository.findByEventId(eventId);
        } catch (Exception e) {
            log.error("Error fetching attendances for event ID: {}", eventId, e);
            throw new RuntimeException("Error fetching attendances for event ID: " + eventId);
        }
    }

    public List<Attendance> getAttendancesByCheckingStaffAccountId(String accountId) {
        try {
            List<CheckingStaff> checkingStaffList = checkingStaffRepository.findByAccountId(Integer.parseInt(accountId));
            if (checkingStaffList.isEmpty()) {
                throw new RuntimeException("No CheckingStaff found for account ID: " + accountId);
            }

            int eventId = checkingStaffList.get(0).getEvent().getId();

            return attendanceRepository.findByEventId(eventId);
        } catch (Exception e) {
            log.error("Error fetching attendances for account ID: {}", accountId, e);
            throw new RuntimeException("Error fetching attendances for account ID: " + accountId);
        }
    }
    public void deleteAttendancesByTicketId(int ticketId) {
        List<Attendance> attendances = attendanceRepository.findByTicket_Id(ticketId);
        if (!attendances.isEmpty()) {
            attendanceRepository.deleteAll(attendances);
        }
    }

    public List<AttendanceDTO> getAttendancesByEventId2(int eventId) {
        try {
            List<Attendance> attendances = attendanceRepository.findByEventId(eventId);

            return attendances.stream().map(attendance -> {
                AttendanceDTO dto = new AttendanceDTO();
                dto.setId(attendance.getId());
                dto.setStatus(attendance.getStatus().name());
                dto.setEventId(attendance.getEventId());
                dto.setEventName(attendance.getEventName());
                dto.setPrice(attendance.getPrice());
                dto.setEventEndDate(attendance.getEventEndDate());
                dto.setDescription(attendance.getDescription());

                TicketDTO ticketDTO = new TicketDTO();
                ticketDTO.setId(attendance.getTicket().getId());
                ticketDTO.setCreatedDate(attendance.getTicket().getCreatedDate());
                ticketDTO.setExpiredDate(attendance.getTicket().getExpiredDate());
                ticketDTO.setStatus(attendance.getTicket().getStatus().name());
                ticketDTO.setEventName(attendance.getTicket().getEventName());
                ticketDTO.setPrice(attendance.getTicket().getPrice());
                ticketDTO.setDescription(attendance.getTicket().getDescription());
                ticketDTO.setEventEndDate(attendance.getTicket().getEventEndDate());

                VisitorDTO visitorDTO = new VisitorDTO();
                visitorDTO.setId(attendance.getTicket().getVisitor().getId());
                visitorDTO.setInformation(attendance.getTicket().getVisitor().getInformation());
                visitorDTO.setAccount_id(attendance.getTicket().getVisitor().getAccount().getId());

                ticketDTO.setVisitor(visitorDTO);

                dto.setTicket(ticketDTO);

                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching attendances for event ID: {}", eventId, e);
            throw new RuntimeException("Error fetching attendances for event ID: " + eventId);
        }
    }
}
