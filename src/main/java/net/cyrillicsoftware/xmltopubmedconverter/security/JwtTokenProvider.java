package net.cyrillicsoftware.xmltopubmedconverter.security;

import io.jsonwebtoken.*;
import net.cyrillicsoftware.xmltopubmedconverter.exception.BasicException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    //generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    //get username from token
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    //validate JWT
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token);
            return true;
        }
        catch (SignatureException exception){
            throw new BasicException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }
        catch (ExpiredJwtException exception){
            throw new BasicException(HttpStatus.BAD_REQUEST, "Expired JWT");
        }
        catch (UnsupportedJwtException exception){
            throw new BasicException(HttpStatus.BAD_REQUEST, "Unsupported JWT");
        }
        catch (IllegalArgumentException exception){
            throw new BasicException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }

    }
}
