package tmdt.turf.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import tmdt.turf.model.enums.Role;
import tmdt.turf.model.enums.TokenType;
import tmdt.turf.util.Util;

import java.security.Key;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final Key key = getSignKey();

    @Override
    public String generateToken(String email) {
        Date currentTime = new Date();
        Date expiredTime = new Date(currentTime.getTime() + Util.ACCESS_TOKEN_TIME * 1000);
        Map<String, String> mapClaims = new HashMap<>();
        mapClaims.put("email", email);
        mapClaims.put("type", TokenType.ACCESS.name());
        return Jwts.builder()
                .setClaims(mapClaims)
                .setIssuedAt(currentTime)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String generateToken(String email, Role role) {
        Date currentTime = new Date();
        Date expiredTime = new Date(currentTime.getTime() + Util.ACCESS_TOKEN_TIME * 1000);
        Map<String, String> mapClaims = new HashMap<>();
        mapClaims.put("email", email);
        mapClaims.put("type", TokenType.ACCESS.name());
        mapClaims.put("role", role.name());
        return Jwts.builder()
                .setClaims(mapClaims)
                .setIssuedAt(currentTime)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String generateRefreshToken(String email) {
        Date currentTime = new Date();
        Date expiredTime = new Date(currentTime.getTime() + Util.REFRESH_TOKEN_TIME * 1000);
        Map<String, String> mapClaims = new HashMap<>();
        mapClaims.put("email", email);
        mapClaims.put("type", TokenType.REFRESH.name());
        return Jwts.builder()
                .setClaims(mapClaims)
                .setIssuedAt(currentTime)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("email").toString();
    }

    @Override
    public Claims getClaimsFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is incorrect or expired");
        }
    }

    @Override
    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public void deleteExpiredAndRevokedRefreshToken() {
//        List<RefreshToken> refreshTokenList = tokenRepository.findByExpiredOrRevoked()
//                .orElseThrow(() -> new RuntimeException("Not found token"));
//        tokenRepository.deleteAll(refreshTokenList);
    }

    private Key getSignKey() {
        String SECRET_KEY = "f847b84084d716c14b051ad6b001624f738f5d302636e6b07cc75e4530af7776a4368a2b586dbefc0564ee28384c2696f178cbed52e62811bcc9ecb59568c996d342db2402038203e";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

