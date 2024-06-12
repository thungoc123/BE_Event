package EventManagement.Event.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHelper {

    @Value("${jwt.private-key}")
    private String key;
    private long plusTime = 8 * 60 * 60 * 1000;

    public String generateToken(String data){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        Date currentDate = new Date();
        long futureTime = currentDate.getTime() + plusTime;
        Date futureDate = new Date(futureTime);

        String token = Jwts.builder()
                .subject(data)
                .expiration(futureDate)
                .signWith(secretKey)
                .compact();

        return token;
    }

    public String decodeToken(String token){
        String roleName = "";
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        try{
            roleName = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
        }catch (Exception e){
            System.out.println("Error decode token fail " + e.getMessage());
        }

        return roleName;
    }

}
