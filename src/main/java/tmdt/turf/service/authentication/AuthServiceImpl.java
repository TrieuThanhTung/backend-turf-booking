package tmdt.turf.service.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tmdt.turf.dto.request.LoginDto;
import tmdt.turf.dto.request.RegisterDto;
import tmdt.turf.dto.request.TokenDto;
import tmdt.turf.exception.CustomException;
import tmdt.turf.model.enums.Role;
import tmdt.turf.model.user.User;
import tmdt.turf.repository.UserRepository;
import tmdt.turf.service.jwt.JwtService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void createNewAccount(RegisterDto registerDto) {
        Optional<User> userOptional = userRepository.findByEmail(registerDto.getEmail());
        if (userOptional.isPresent()) {
            throw new CustomException("User is existed", HttpStatus.EXPECTATION_FAILED);
        }
        User newUser = User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .role(Role.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .enabled(true)
                .build();
        userRepository.save(newUser);
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new CustomException("User is existed", HttpStatus.EXPECTATION_FAILED));
        if (!user.getEnabled()) {
            throw new CustomException("Login FAIL!. Account not FOUND.", HttpStatus.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new CustomException("Login FAIL!. Email or password not match.", HttpStatus.NOT_FOUND);
        }
        return TokenDto.builder()
                .accessToken(jwtService.generateToken(user.getId(), user.getEmail(), user.getRole()))
                .refreshToken("Refresh token")
                .build();
    }
}
