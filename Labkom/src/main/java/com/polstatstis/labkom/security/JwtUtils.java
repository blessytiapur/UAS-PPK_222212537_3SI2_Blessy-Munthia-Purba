package com.polstatstis.labkom.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    private String jwtSecret = "secretkey";
    private int jwtExpirationMs = 86400000;

    public JwtUtils() {
    }

    public String getRoleFromJwtToken(String token) {
        return (String)((Claims)Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody()).get("role", String.class);
    }

    public String generateJwtToken(String username, String role) {
        return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + (long)this.jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, this.jwtSecret).compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return ((Claims)Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody()).getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }
}

