package users.rishik.SpringAuthStarter.jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import users.rishik.SpringAuthStarter.exceptions.InvalidJwtException;
import users.rishik.SpringAuthStarter.exceptions.JwtExpiredException;
import users.rishik.SpringAuthStarter.user.standard.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Slf4j
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
        log.info("Generating token for username: {}", user.getEmail());
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
        try {
            return Jwts
                    .parser()
                    .verifyWith(this.getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ex){
            throw new JwtExpiredException("Jwt Expired");
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException ex){
            throw new InvalidJwtException("Jwt invalid. " + ex.getMessage());
        }
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
        log.info("Validating JWT for user: {}", email);
        return email.equals(extractUsername(token)) && !this.isExpired(token);
    }
}
