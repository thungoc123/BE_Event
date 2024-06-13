package EventManagement.Event.controller;

import EventManagement.Event.DTO.PaymentRestDTO;
import EventManagement.Event.config.VnPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/vnpay")
public class VNPayController {

    private static final Logger logger = LoggerFactory.getLogger(VNPayController.class);

    @GetMapping("/pay")
    public ResponseEntity<?> pay() {
        try {
            long amount = 1000000;

            String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
            String vnp_IpAddr = "127.0.0.1";  // Replace this with actual IP retrieval logic
            String vnp_TmnCode = VnPayConfig.vnp_TmnCode;
            String returnUrl = "https://yourdomain.com/return_url"; // Replace with your actual return URL

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", VnPayConfig.vnp_Version);
            vnp_Params.put("vnp_Command", VnPayConfig.vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_OrderType", "other"); // Use the appropriate order type
            vnp_Params.put("vnp_ReturnUrl", returnUrl);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = dateFormatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 30);
            String vnp_ExpireDate = dateFormatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
            String expirationTime = timeFormatter.format(cld.getTime());

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            for (String fieldName : fieldNames) {
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && fieldValue.length() > 0) {
                    hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    hashData.append('&');
                    query.append('&');
                }
            }

            String queryUrl = query.substring(0, query.length() - 1); // Remove last '&'
            String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;

            PaymentRestDTO restDTO = new PaymentRestDTO();
            restDTO.setStatus("ok");
            restDTO.setMessage("successfully");
            restDTO.setURL(paymentUrl);
            restDTO.setExpirationTime(expirationTime); // Set the expiration time in the DTO

            return ResponseEntity.status(HttpStatus.OK).body(restDTO);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.error("Error processing payment", e);
            // Return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment: " + e.getMessage());
        }
    }
}
