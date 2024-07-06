package EventManagement.Event.service.imp;

import EventManagement.Event.DTO.PaymentRestDTO;

public interface VNPayServiceImp {
    PaymentRestDTO processPayment(long amount);
    PaymentRestDTO processPayment(long amount, String email); // Add this line
}
