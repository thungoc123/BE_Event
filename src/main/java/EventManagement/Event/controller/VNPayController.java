package EventManagement.Event.controller;

import EventManagement.Event.DTO.PaymentRestDTO;
import EventManagement.Event.service.imp.VNPayServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vnpay")
public class VNPayController {

    private final VNPayServiceImp vnPayService;

    @Autowired
    public VNPayController(VNPayServiceImp vnPayService) {
        this.vnPayService = vnPayService;
    }

    @GetMapping("/pay")
    public ResponseEntity<?> pay() {
        try {
            long amount = 20000;
            PaymentRestDTO paymentResponse = vnPayService.processPayment(amount);
            return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment: " + e.getMessage());
        }
    }
}