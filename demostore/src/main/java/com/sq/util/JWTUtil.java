package com.sq.util;

import com.sq.pojo.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    public enum Time {
        HOUR(3600), DAY(86400), MONTH(2628000);
        private final long value;
        Time(final long newValue) {
            value = newValue;
        }
        public long getValue() { return value; }
    }
    private static final String CLAIM_KEY_UID = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;

    /**
     * generate token based on claims
     */
    private String generateToken(Map<String, Object> claims, Time time) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate(time))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * get payload from token
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {

        }
        return claims;
    }

    /**
     * generate token's expiry time
     */
    private Date generateExpirationDate(Time time) {
        return new Date(System.currentTimeMillis() + time.value * 1000);
    }

    /**
     * get username from token
     */
    public String getUidFromToken(String token) {
        String uid = "";
        try {
            Claims claims = getClaimsFromToken(token);
            uid = claims.getSubject();
        } catch (Exception e) {
            uid = null;
        }
        return uid;
    }

    /**
     * validate token
     *
     * @param token       token from client
     * @param userDetails
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        return !isTokenExpired(token);
    }

    /**
     * check token expiry
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * expiry date
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * generate token
     */
    public String generateToken(Member member, Time time) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_UID, member.getId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims, time);
    }

//    /**
//     * check if token can be refreshed
//     */
//    public boolean canRefresh(String token) {
//        return !isTokenExpired(token);
//    }
//
//    /**
//     * refresh token
//     */
//    public String refreshToken(String token) {
//        Claims claims = getClaimsFromToken(token);
//        claims.put(CLAIM_KEY_CREATED, new Date());
//        return generateToken(claims);
//    }

}
