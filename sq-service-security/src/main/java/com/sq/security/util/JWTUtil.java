package com.sq.security.util;

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
    private static final String CLAIM_KEY_UID = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * generate token based on claims
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
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
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
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
        // get the uid
        return getUidFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
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
    public String generateToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_UID, member.getId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * check if token can be refreshed
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * refresh token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

}
