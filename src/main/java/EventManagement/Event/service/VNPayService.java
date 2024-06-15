package EventManagement.Event.service;

import EventManagement.Event.DTO.PaymentRestDTO;
import EventManagement.Event.config.VnPayConfig;
import EventManagement.Event.service.imp.VNPayServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService implements VNPayServiceImp {
    private static final Logger logger = LoggerFactory.getLogger(VNPayService.class);
    private static final long MAX_AMOUNT = 1000000000L; // Maximum allowed amount

    @Override
    public PaymentRestDTO processPayment(long amount) {
        if (amount > MAX_AMOUNT) {
            PaymentRestDTO restDTO = new PaymentRestDTO();
            restDTO.setStatus("error");
            restDTO.setMessage("The amount is too big, please do another payment.");
            restDTO.setURL(null);
            restDTO.setExpirationTime(null);
            restDTO.setExpirationDate(null);
            restDTO.setTimeZone(null);
            return restDTO;
        }

        try {
            long vnpAmount = amount * 100;
            String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
            String vnp_IpAddr = "127.0.0.1";  // Replace this with actual IP retrieval logic
            String vnp_TmnCode = VnPayConfig.vnp_TmnCode;
            String returnUrl = "https://yourdomain.com/return_url"; // Replace with your actual return URL

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", VnPayConfig.vnp_Version);
            vnp_Params.put("vnp_Command", VnPayConfig.vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(vnpAmount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_OrderType", "other"); // Use the appropriate order type
            vnp_Params.put("vnp_ReturnUrl", returnUrl);

            // Set time zone
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Calendar cld = Calendar.getInstance(timeZone);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
            dateFormatter.setTimeZone(timeZone);
            String vnp_CreateDate = dateFormatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 30);
            String vnp_ExpireDate = dateFormatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Convert vnp_ExpireDate to yyyy/MM/dd format
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            inputDateFormat.setTimeZone(timeZone);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date expireDate = inputDateFormat.parse(vnp_ExpireDate);
            String formattedExpireDate = outputDateFormat.format(expireDate);

            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
            timeFormatter.setTimeZone(timeZone);
            String expirationTime = timeFormatter.format(cld.getTime());
            String locationTakeTime = timeFormatter.format(new Date()); // Current server time

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;

            PaymentRestDTO restDTO = new PaymentRestDTO();
            restDTO.setStatus("ok");
            restDTO.setMessage("successfully");
            restDTO.setURL(paymentUrl);
            restDTO.setExpirationTime(expirationTime); // Set the expiration time in the DTO
            restDTO.setExpirationDate(formattedExpireDate); // Set the formatted expiration date
            restDTO.setTimeZone(timeZone.getID());  // Set the time zone in the DTO

            return restDTO;
        } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.error("Error processing payment", e);
            // Return an error response
            throw new RuntimeException("Error processing payment: " + e.getMessage(), e);
        }
    }
}