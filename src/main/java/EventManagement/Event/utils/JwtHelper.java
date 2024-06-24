package EventManagement.Event.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtHelper {

    @Value("${jwt.private-key}")
    private String key;
    private long plusTime = 8 * 60 * 60 * 1000;

    public String generateToken(String accountId, String role) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        Date currentDate = new Date();
        long futureTime = currentDate.getTime() + plusTime;
        Date futureDate = new Date(futureTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("accountId", accountId);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(accountId)
                .setExpiration(futureDate)
                .signWith(secretKey)
                .compact();
    }

    public Map<String, String> decodeToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
      
        Map<String, String> claimsMap = new HashMap<>();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String accountId = claims.get("accountId", String.class);
            String role = claims.get("role", String.class);

            claimsMap.put("accountId", accountId);
            claimsMap.put("role", role);

        } catch (JwtException e) {
            System.out.println("Error decode token fail: " + e.getMessage());
        }
        return claimsMap;
    }

}
