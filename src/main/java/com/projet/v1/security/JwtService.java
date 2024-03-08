package com.projet.v1.security;

import com.projet.v1.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String encriptionKey;
    @Value("${application.security.jwt.expiration}")
    private long expiration;
    @Value("${application.security.jwt.expirationLong}")
    private long expirationLong;

    @Autowired
    private UserService userService;

    public String generate (String pseudo){
        UserDetails user = userService.loadUserByUsername(pseudo);
        return buildToken(user, this.expiration);
    }
    public String generateLong (String pseudo){
        UserDetails user = userService.loadUserByUsername(pseudo);
        return buildToken(user, this.expirationLong);
    }

    private String buildToken(UserDetails user, long expiration){

        Date d = new Date();
        log.info(d.toString());
        final long currentTime = d.getTime();
        final long expirationTime = currentTime + expiration;

        final Map<String, String> claims = Map.of(
                Claims.SUBJECT, user.getUsername(),
                Claims.ISSUED_AT, String.valueOf(currentTime / 1000),
                Claims.EXPIRATION, String.valueOf(expirationTime / 1000)
        );

        log.info("Current Time for Jwt token : " + currentTime);
        log.info("ExpirationDate for Jwt token : " + expirationTime);

        return Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getUsername())
                .setClaims(claims)
                .signWith(getKey() , SignatureAlgorithm.HS384)
                .compact();
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(encriptionKey);
        return Keys.hmacShaKeyFor(decoder);
    }

    public String extractPseudo(String token){
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        log.info("expiration date : " + expirationDate);
        return expirationDate.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {

        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
