package EventManagement.Event.service.imp;

import EventManagement.Event.DTO.PaymentRestDTO;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface VNPayServiceImp {
    PaymentRestDTO processPayment(long amount);
    PaymentRestDTO processPayment(long amount, String email);
    boolean validateVnpayResponse(Map<String, String> allParams) throws UnsupportedEncodingException;// Add this line
}
