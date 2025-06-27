package users.rishik.SpringAuthStarter.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import users.rishik.SpringAuthStarter.Entities.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@ConfigurationProperties(prefix = "jwt")
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    JwtService(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(this.jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    private Date getExpirationDate(){
        long time = new Date().getTime() + this.jwtConfig.getExpiration();
        return new Date(time);
    }

    public String generateToken(User user){
        return Jwts
                .builder()
                .claim("userId", user.getId())
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(this.getExpirationDate())
                .signWith(this.getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    private Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = this.extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public int extractUserId(String token) { return (Integer) extractClaims(token).get("userId"); }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, String email) {
        return email.equals(extractUsername(token)) && !this.isExpired(token);
    }
}
