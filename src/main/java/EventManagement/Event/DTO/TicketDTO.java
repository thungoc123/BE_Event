package EventManagement.Event.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TicketDTO {
    private int id;
    private int quantity;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
    private String status;
    private String eventName;
    private double price;
    private String description;
    private LocalDateTime eventEndDate;

    // Thêm thông tin từ Cart và Visitor
    private CartDTO cart;
    private VisitorDTO visitor;
}
