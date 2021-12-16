package uz.pdp.lesson_5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.lesson_5.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    public String generateToken(String username, Set<Role> roles) {
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, "Assalamu1")
                .compact();
        return token;
    }

    public String getEmailFromToken(String token) {
        try {
            String email = Jwts
                    .parser()
                    .setSigningKey("Assalamu1")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return email;
        } catch (Exception exception) {
            return null;
        }
    }
}
