package tmdt.turf.service.jwt;

import io.jsonwebtoken.Claims;
import tmdt.turf.model.enums.Role;

public interface JwtService {
    String generateToken(String email);

    String generateToken(Integer id, String email, Role role);

    String generateRefreshToken(String subject);

    String getEmailFromJWT(String token);

    Claims getClaimsFromJWT(String token);

    boolean validateToken(String token);

    boolean validateRefreshToken(String token);

    void deleteExpiredAndRevokedRefreshToken();
}
