package tmdt.turf.service.authentication;

import tmdt.turf.dto.request.LoginDto;
import tmdt.turf.dto.request.RegisterDto;
import tmdt.turf.dto.request.TokenDto;

public interface AuthService {
    void createNewAccount(RegisterDto registerDto);

    TokenDto login(LoginDto loginDto);
}
