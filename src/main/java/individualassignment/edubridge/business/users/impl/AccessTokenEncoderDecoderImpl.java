package individualassignment.edubridge.business.users.impl;

import individualassignment.edubridge.business.users.AccessTokenDecoder;
import individualassignment.edubridge.business.users.AccessTokenEncoder;
import individualassignment.edubridge.business.users.exceptions.ExpiredAccessTokenException;
import individualassignment.edubridge.business.users.exceptions.InvalidAccessTokenException;
import individualassignment.edubridge.domain.users.AccessToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(accessToken.getRoles())) {
            claimsMap.put("roles", accessToken.getRoles());
        }
        if (accessToken.getStudentId() != null) {
            claimsMap.put("studentId", accessToken.getStudentId());
        }
        if (accessToken.getTeacherId() != null) {
            claimsMap.put("teacherId", accessToken.getTeacherId());
        }
        if (accessToken.getAdminId() != null) {
            claimsMap.put("adminId", accessToken.getAdminId());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessTokenEncoded);
            Claims claims = jwt.getBody();

            List<String> roles = claims.get("roles", List.class);

            return AccessToken.builder()
                    .subject(claims.getSubject())
                    .roles(roles)
                    .studentId(claims.get("studentId", Long.class))
                    .teacherId(claims.get("teacherId", Long.class))
                    .adminId(claims.get("adminId", Long.class))
                    .build();
        }
        catch (ExpiredJwtException e)
        {
            throw new ExpiredAccessTokenException(e.getMessage());
        }
        catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }

    }
}

