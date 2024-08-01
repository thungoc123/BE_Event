package EventManagement.Event.controller;

import EventManagement.Event.DTO.PaymentRestDTO;
import EventManagement.Event.entity.Event;
import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.entity.SponsorEvent;
import EventManagement.Event.entity.SponsorEventId;
import EventManagement.Event.repository.EventRepository;
import EventManagement.Event.repository.SponsorEventRepository;
import EventManagement.Event.repository.SponsorRepository;
import EventManagement.Event.service.imp.VNPayServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/vnpay")
public class VNPayController {
    @Autowired
    private SponsorEventRepository sponsorEventRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SponsorRepository sponsorRepository;

    private final VNPayServiceImp vnPayService;

    @Autowired
    public VNPayController(VNPayServiceImp vnPayService) {
        this.vnPayService = vnPayService;
    }

    @GetMapping("/pay")
    public ResponseEntity<?> pay(@RequestParam long amount, @RequestParam String email) {
        try {
            PaymentRestDTO paymentResponse = vnPayService.processPayment(amount, email);
            return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment: " + e.getMessage());
        }
    }
    @GetMapping("/pay/capital")
    public ResponseEntity<?> pay(@RequestParam long amount, @RequestParam String email, @RequestParam int eventId, @RequestParam Long sponsorId) {
        try {
            System.out.println("Received payment request: amount=" + amount + ", email=" + email + ", eventId=" + eventId + ", sponsorId=" + sponsorId);

            PaymentRestDTO paymentResponse = vnPayService.processPayment(amount, email);
//            if (!"ok".equalsIgnoreCase(paymentResponse.getStatus())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("Payment failed: " + paymentResponse.getMessage());
//            }
//            return ResponseEntity.status(HttpStatus.OK).body(paymentResponse.getURL());
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error processing payment: " + e.getMessage());
//        }
//    }


            // Tìm SponsorEvent theo eventId và sponsorId
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + eventId));

            Sponsor sponsor = sponsorRepository.findById(sponsorId)
                    .orElseThrow(() -> new NoSuchElementException("Sponsor not found for ID: " + sponsorId));

            List<SponsorEvent> sponsorEvents = sponsorEventRepository.findByEventAndSponsor(event, sponsor);

            if (!sponsorEvents.isEmpty()) {
                SponsorEvent sponsorEvent = sponsorEvents.get(0); // Hoặc xử lý các đối tượng trong danh sách nếu cần
                // Lưu số tiền thanh toán vào contributedCapital
                if (amount > Integer.MAX_VALUE || amount < Integer.MIN_VALUE) {
                    throw new IllegalArgumentException("Amount out of range for Integer: " + amount);
                }
                int amountAsInt = (int) amount;
                sponsorEvent.setContributedCapital(amountAsInt);
                sponsorEventRepository.save(sponsorEvent);

                return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
            } else {
                // Tạo mới SponsorEvent nếu chưa tồn tại
                SponsorEvent newSponsorEvent = new SponsorEvent();
                newSponsorEvent.setEvent(event);
                newSponsorEvent.setSponsor(sponsor);
                int amountAsInt = (int) amount;
                 // Gán giá trị mặc định cho profitPercent nếu cần
                newSponsorEvent.setContributedCapital(amountAsInt); // Gán số tiền thanh toán
                sponsorEventRepository.save(newSponsorEvent);

                return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment: " + e.getMessage());
        }
    }

//    @GetMapping("/vnpay-callback")
//    public ResponseEntity<String> vnPayCallback(@RequestParam Map<String, String> allParams) {
//        try {
//            boolean isValid = vnPayService.validateVnpayResponse(allParams);
//            if (isValid && "00".equals(allParams.get("vnp_ResponseCode"))) {
//                long amount = Long.parseLong(allParams.get("vnp_Amount")) / 100;
//                int eventId = Integer.parseInt(allParams.get("vnp_OrderInfo").split(":")[1].trim());
//                long sponsorId = Long.parseLong(allParams.get("vnp_OrderInfo").split(":")[2].trim());
//
//
//                Event event = eventRepository.findById(eventId)
//                        .orElseThrow(() -> new NoSuchElementException("Event not found for ID: " + eventId));
//
//                Sponsor sponsor = sponsorRepository.findById(sponsorId)
//                        .orElseThrow(() -> new NoSuchElementException("Sponsor not found for ID: " + sponsorId));
//
//                List<SponsorEvent> sponsorEvents = sponsorEventRepository.findByEventAndSponsor(event, sponsor);
//
//                if (!sponsorEvents.isEmpty()) {
//                    SponsorEvent sponsorEvent = sponsorEvents.get(0);
//                    if (amount > Integer.MAX_VALUE || amount < Integer.MIN_VALUE) {
//                        throw new IllegalArgumentException("Amount out of range for Integer: " + amount);
//                    }
//                    int amountAsInt = (int) amount;
//                    sponsorEvent.setContributedCapital(amountAsInt);
//                    sponsorEventRepository.save(sponsorEvent);
//                } else {
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                            .body("SponsorEvent not found for eventId=" + eventId + " and sponsorId=" + sponsorId);
//                }
//
//                return ResponseEntity.status(HttpStatus.OK).body("Payment successfully processed.");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment response.");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error processing payment callback: " + e.getMessage());
//        }
//    }
}
