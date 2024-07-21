package EventManagement.Event.service;

import EventManagement.Event.entity.*;
import EventManagement.Event.repository.EventProfitRepository;
import EventManagement.Event.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AttendanceService attendanceService;  // Inject AttendanceService

    @Autowired
    private EventProfitRepository eventProfitRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteById(int id) {
        ticketRepository.deleteById(id);
    }

    public boolean deleteTicket(int id) {
        Optional<Ticket> ticketOptional = findById(id);
        if (ticketOptional.isPresent()) {
            deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Map<String, String> updateTicketStatusandAttendence(int id, Ticket.Status status) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<Ticket> ticketOptional = findById(id);
            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                ticket.setStatus(status);
                ticketRepository.save(ticket);

                if (status == Ticket.Status.PAID) {
                    attendanceService.createAttendancesForEvent(ticket.getEvent().getId());
                    response.put("message", "Paiding successfully so you are in attendence");
                } else if (status == Ticket.Status.CANCELLED) {
                    response.put("message", "Cancel Ticket successfully");
                } else if (status == Ticket.Status.PENDING) {
                    response.put("message", "Create Ticket successfully");
                }

                return response;
            } else {
                response.put("message", "Ticket not found for given ID: " + id);
                return response;
            }
        } catch (Exception e) {
            response.put("message", "Error occurred while updating the ticket: " + e.getMessage());
            e.printStackTrace();
            return response;
        }
    }

    public boolean deleteTicketsByEventId(int eventId) {
        List<Ticket> tickets = ticketRepository.findByEvent_Id(eventId);
        if (!tickets.isEmpty()) {
            for (Ticket ticket : tickets) {
                // Xóa attendances liên quan đến ticket
                attendanceService.deleteAttendancesByTicketId(ticket.getId());
            }
            ticketRepository.deleteAll(tickets);
            return true;
        }
        return false;
    }

//    public Optional<Map<String, String>> createOrderTicket(Integer visitorId, Integer eventId, boolean statusCart, Ticket.Status status) {
//        Map<String, String> response = new HashMap<>();
//
//        try {
//            Optional<Visitor> visitorOptional = visitorService.findById(visitorId);
//            Optional<Event> eventOptional = eventService.findById(eventId);
//
//            if (visitorOptional.isPresent() && eventOptional.isPresent()) {
//                Visitor visitor = visitorOptional.get();
//                Event event = eventOptional.get();
//
//                // Check if the visitor already has a ticket for this event
//                Optional<Ticket> existingTicket = ticketRepository.findByVisitor_IdAndEvent_Id(visitorId, eventId);
//                if (existingTicket.isPresent()) {
//                    response.put("message", "Visitor already has a ticket for this event.");
//                    return Optional.of(response);
//                }
//
//                if (LocalDateTime.now().isBefore(event.getTimeclosesale())) {
//                    Ticket ticket = new Ticket();
//                    ticket.setVisitor(visitor);
//                    ticket.setEvent(event);
//                    ticket.setCreatedDate(LocalDateTime.now());
//                    ticket.setExpiredDate(event.getTimeclosesale());
//                    ticket.setStatus(status); // Use the provided status
//                    ticket.setStatusCart(statusCart);
//
//                    Ticket savedTicket = ticketRepository.save(ticket);
//                    response.put("message", "Ticket created successfully");
//                    response.put("TicketId", String.valueOf(savedTicket.getId()));
//                    return Optional.of(response);
//                } else {
//                    response.put("message", "Cannot create ticket for expired event.");
//                    return Optional.of(response);
//                }
//            } else {
//                response.put("message", "Visitor or Event not found for given IDs.");
//                return Optional.of(response);
//            }
//        } catch (Exception e) {
//            response.put("message", "Error occurred while creating the ticket: " + e.getMessage());
//            e.printStackTrace();
//            return Optional.of(response);
//        }
//    }

//    public Optional<Map<String, String>> createOrderTicket(Integer visitorId, Integer eventId, boolean statusCart, Ticket.Status status) {
//        Map<String, String> response = new HashMap<>();
//
//        try {
//            Optional<Visitor> visitorOptional = visitorService.findById(visitorId);
//            Optional<Event> eventOptional = eventService.findById(eventId);
//
//            if (visitorOptional.isPresent() && eventOptional.isPresent()) {
//                Visitor visitor = visitorOptional.get();
//                Event event = eventOptional.get();
//
//                // Check if the visitor already has a ticket for this event
//                Optional<Ticket> existingTicket = ticketRepository.findByVisitor_IdAndEvent_Id(visitorId, eventId);
//                if (existingTicket.isPresent() && existingTicket.get().getStatus() != Ticket.Status.CANCELLED) {
//                    response.put("message", "Visitor already has a ticket for this event.");
//                    return Optional.of(response);
//                }
//
//                if (LocalDateTime.now().isBefore(event.getTimeclosesale())) {
//                    Ticket ticket = new Ticket();
//                    ticket.setVisitor(visitor);
//                    ticket.setEvent(event);
//                    ticket.setCreatedDate(LocalDateTime.now());
//                    ticket.setExpiredDate(event.getTimeclosesale());
//                    ticket.setStatus(status); // Use the provided status
//                    ticket.setStatusCart(statusCart);
//
//                    Ticket savedTicket = ticketRepository.save(ticket);
//                    response.put("message", "Ticket created successfully");
//                    response.put("TicketId", String.valueOf(savedTicket.getId()));
//                    return Optional.of(response);
//                } else {
//                    response.put("message", "Cannot create ticket for expired event.");
//                    return Optional.of(response);
//                }
//            } else {
//                response.put("message", "Visitor or Event not found for given IDs.");
//                return Optional.of(response);
//            }
//        } catch (Exception e) {
//            response.put("message", "Error occurred while creating the ticket: " + e.getMessage());
//            e.printStackTrace();
//            return Optional.of(response);
//        }
//    }

    public Optional<Map<String, String>> createOrderTicket(Integer visitorId, Integer eventId, boolean statusCart, Ticket.Status status) {
        Map<String, String> response = new HashMap<>();

        try {
            Optional<Visitor> visitorOptional = visitorService.findById(visitorId);
            Optional<Event> eventOptional = eventService.findById(eventId);

            if (visitorOptional.isPresent() && eventOptional.isPresent()) {
                Visitor visitor = visitorOptional.get();
                Event event = eventOptional.get();

                // Check if the visitor already has a ticket for this event with status PAID or PENDING
                List<Ticket> existingTickets = ticketRepository.findByVisitor_IdAndEvent_Id(visitorId, eventId);
                boolean hasPaidOrPendingTicket = existingTickets.stream()
                        .anyMatch(ticket -> ticket.getStatus() == Ticket.Status.PAID || ticket.getStatus() == Ticket.Status.PENDING);

                if (hasPaidOrPendingTicket) {
                    response.put("message", "Visitor already has a ticket for this event.");
                    return Optional.of(response);
                }

                if (LocalDateTime.now().isBefore(event.getTimeclosesale())) {
                    Ticket ticket = new Ticket();
                    ticket.setVisitor(visitor);
                    ticket.setEvent(event);
                    ticket.setCreatedDate(LocalDateTime.now());
                    ticket.setExpiredDate(event.getTimeclosesale());
                    ticket.setStatus(status); // Use the provided status
                    ticket.setStatusCart(statusCart);

                    Ticket savedTicket = ticketRepository.save(ticket);
                    response.put("message", "Ticket created successfully");
                    response.put("TicketId", String.valueOf(savedTicket.getId()));
                    return Optional.of(response);
                } else {
                    response.put("message", "Cannot create ticket for expired event.");
                    return Optional.of(response);
                }
            } else {
                response.put("message", "Visitor or Event not found for given IDs.");
                return Optional.of(response);
            }
        } catch (Exception e) {
            response.put("message", "Error occurred while creating the ticket: " + e.getMessage());
            e.printStackTrace();
            return Optional.of(response);
        }
    }

    public Optional<Map<String, Object>> countPaidTicketsByEventId(int eventId) {
        long count = ticketRepository.countPaidTicketsByEventId(eventId);
        Optional<Event> eventOptional = eventService.findById(eventId);
        Map<String, Object> response = new HashMap<>();
        if (eventOptional.isPresent()) {
            response.put("Amount", count);
        } else {
            response.put("error", "Event not found");
        }
        return Optional.of(response);
    }

    public List<Object> findVisitorsByEventIdAndStatusPaid(int eventId) {
        List<Visitor> visitors = ticketRepository.findVisitorsByEventIdAndStatusPaid(eventId);
        if (visitors == null || visitors.isEmpty()) {
            return Collections.singletonList("Data is null");
        }
        return new ArrayList<>(visitors);
    }

    public Optional<Map<String, Object>> viewTicketsByEventAndDate(int eventId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Ticket> tickets = ticketRepository.findByEvent_IdAndCreatedDateBetween(eventId, startDate, endDate);
        Optional<Event> eventOptional = eventService.findById(eventId);

        if (!eventOptional.isPresent()) {
            return Optional.empty();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        Map<String, Object> response = new HashMap<>();
        response.put("startDate", startDate.format(formatter));
        response.put("endDate", endDate.format(formatter));
        response.put("amount", tickets.size());

        List<Map<String, Object>> visitorList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Map<String, Object> visitorInfo = new HashMap<>();
            Visitor visitor = ticket.getVisitor();
            visitorInfo.put("visitorId", visitor.getId());
            visitorList.add(visitorInfo);
        }
        response.put("visitorList", visitorList);

        return Optional.of(response);
    }

    public Optional<Map<String, Object>> countTicketsByEventIdAndDate(int eventId, LocalDateTime dateTime) {
        Optional<Event> eventOptional = eventService.findById(eventId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            LocalDate date = dateTime.toLocalDate();
            if (date.isAfter(event.getTimeopensale().toLocalDate()) && date.isBefore(event.getTimeclosesale().toLocalDate())) {
                long count = ticketRepository.countTicketsByEventIdAndDate(eventId, date);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                Map<String, Object> response = new HashMap<>();
                response.put("Date", dateTime.format(formatter));
                response.put("Amount Ticket on Date", count);
                return Optional.of(response);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public Map<String, Object> getVisitorEmailAndTicketPrice(int ticketId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);

        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            Visitor visitor = ticket.getVisitor();
            Account account = visitor.getAccount();
            String email = account.getEmail();
            double price = ticket.getPrice();
            int visitorId = visitor.getId(); // Get the visitor ID

            response.put("email", email);
            response.put("price", price);
            response.put("visitorId", visitorId); // Add the visitor ID to the response
        } else {
            response.put("message", "Ticket not found");
        }

        return response;
    }

    public List<Ticket> getTicketsByStatusAndCart(Ticket.Status status) {
        return ticketRepository.findByStatusAndStatusCart(status, true);
    }

    public List<Ticket> getTicketsInCart() {
        return ticketRepository.findByStatusCart(true);
    }

    public List<Ticket> getTicketsInCartByVisitorId(int visitorId) {
        return ticketRepository.findByVisitorIdAndStatusCart(visitorId);
    }

    public Optional<List<Map<String, Object>>> findVisitorIdsByAccountId(int accountId) {
        List<Visitor> visitors = visitorService.findByAccountId(accountId);
        if (!visitors.isEmpty()) {
            List<Map<String, Object>> responseList = new ArrayList<>();
            for (Visitor visitor : visitors) {
                Map<String, Object> response = new HashMap<>();
                response.put("visitorId", visitor.getId());
                responseList.add(response);
            }
            return Optional.of(responseList);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Map<String, Object>> calculateTotalAmountRaised(int eventId) {
        Optional<Event> eventOptional = eventService.findById(eventId);
        if (!eventOptional.isPresent()) {
            return Optional.empty();
        }

        double totalAmount = ticketRepository.findByEvent_Id(eventId)
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();

        // Gọi phương thức để cập nhật bảng event_profit
        updateEventProfit(eventId, totalAmount);

        Map<String, Object> response = new HashMap<>();
        response.put("eventId", eventId);
        response.put("totalAmountRaised", totalAmount);

        return Optional.of(response);
    }

    private void updateEventProfit(int eventId, double totalAmount) {
        // Debug: Kiểm tra xem có tìm thấy EventProfit không
        EventProfit eventProfit = eventProfitRepository.findByEventId(eventId)
                .orElse(new EventProfit());

        // Debug: Kiểm tra giá trị
        System.out.println("Updating EventProfit for Event ID: " + eventId + " with totalAmount: " + totalAmount);

        eventProfit.setEventId(eventId);
        eventProfit.setTotalProfit(totalAmount);

        // Debug: Kiểm tra trước khi lưu
        System.out.println("Saving EventProfit: " + eventProfit);

        eventProfitRepository.save(eventProfit);
    }

    public Optional<Map<String, Object>> countTotalParticipants(int eventId) {
        Optional<Event> eventOptional = eventService.findById(eventId);
        if (!eventOptional.isPresent()) {
            return Optional.empty();
        }

        long totalParticipants = ticketRepository.countDistinctByEvent_Id(eventId);

        Map<String, Object> response = new HashMap<>();
        response.put("eventId", eventId);
        response.put("totalParticipants", totalParticipants);

        return Optional.of(response);
    }
}

