package com.example.demoSecurity.security;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "chuoibimat";
    private final long JWT_EXP = 604800000L;
    public String generateToken(CustomUserDetails userDetails){
        Date now = new Date();
        Date exp = new Date(now.getTime()+JWT_EXP);
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validation(String authToken){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        }
        catch(MalformedJwtException ex){
            log.error("Invalid JWT token");
        }
        catch(ExpiredJwtException ex){
            log.error("Expired JWT token");
        }
        catch(UnsupportedJwtException ex){
            log.error("Unsupported JWT token");
        }
        catch(IllegalArgumentException ex){
            log.error(" JWT claims is empty");
        }
        return false;
    }
}
