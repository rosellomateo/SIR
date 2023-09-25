package ar.edu.uade.tpoapi.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.token.expiration.time}")
    private String timeExpiration;

    //Generar token de acceso

    public String generateAccesToken(String mail) {
        return Jwts.builder()
        .setSubject(mail)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
        .signWith(getSignaturKey(),SignatureAlgorithm.HS512)
        .compact();
    }

    //Validar token de acceso
    public Boolean validateAccesToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignaturKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Obtener documento del token
    public String getDocumentoFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Obtener un solo claim del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsFunction) {
        Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    //Obtener todos los claims del token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignaturKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Obtener firma del token
    public Key getSignaturKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
