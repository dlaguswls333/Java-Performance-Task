package com.dgsw.javaperformancetask.common.jwt;

import com.dgsw.javaperformancetask.common.exception.CustomException;
import com.dgsw.javaperformancetask.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    private static final long ACCESS_TOKEN_MILLIS = 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_MILLIS = 14 * 24 * 60 * 60 * 1000L;
    private final SecretKey key;

    public JwtProvider(SecretKey jwtSecretKey) { this.key = jwtSecretKey; }

    public String generateAccessToken(String username) {
        return generate(username, "access", ACCESS_TOKEN_MILLIS);
    }

    public String generateRefreshToken(String username) {
        return generate(username, "refresh", REFRESH_TOKEN_MILLIS);
    }

    private String generate(String username, String type, long lifetime) {
        Date now = new Date();
        return Jwts.builder().subject(username).claim("type", type)
                .issuedAt(now).expiration(new Date(now.getTime() + lifetime))
                .signWith(key).compact();
    }

    public Claims parseAccessToken(String token) { return parseType(token, "access"); }
    public Claims parseRefreshToken(String token) { return parseType(token, "refresh"); }

    private Claims parseType(String token, String expectedType) {
        try {
            Claims claims = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token).getPayload();
            if (!expectedType.equals(claims.get("type", String.class))) {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }
            return claims;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
}
