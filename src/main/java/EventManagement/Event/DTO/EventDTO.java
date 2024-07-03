package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EventDTO {
    private int id;
    private String name;
    private String description;
    private double price;
    private LocalDateTime timestart;
    private LocalDateTime timeend;
    private LocalDateTime timeopensale;
    private LocalDateTime timeclosesale;
    //private AccountDTO account; // Nếu cần, có thể sử dụng AccountDTO thay vì Account
    //private SponsorDTO sponsor; // Tương tự với Sponsor
    //private StateEventDTO stateEvent; // Tương tự với StateEvent
    //private List<EventImageDTO> eventImages; // Có thể sử dụng EventImageDTO
    //private List<EventScheduleDTO> eventSchedules; // Tương tự với EventSchedule
    //private List<CheckingStaffDTO> eventCheckingStaffs; // Tương tự với CheckingStaffDTO
    //private Set<SponsorProgramDTO> sponsorPrograms; // Tương tự với SponsorProgramDTO
    //private Set<TicketDTO> tickets; // Tương tự với TicketDTO


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getTimestart() {
        return timestart;
    }

    public void setTimestart(LocalDateTime timestart) {
        this.timestart = timestart;
    }

    public LocalDateTime getTimeend() {
        return timeend;
    }

    public void setTimeend(LocalDateTime timeend) {
        this.timeend = timeend;
    }

    public LocalDateTime getTimeopensale() {
        return timeopensale;
    }

    public void setTimeopensale(LocalDateTime timeopensale) {
        this.timeopensale = timeopensale;
    }

    public LocalDateTime getTimeclosesale() {
        return timeclosesale;
    }

    public void setTimeclosesale(LocalDateTime timeclosesale) {
        this.timeclosesale = timeclosesale;
    }
}
