package tmdt.turf.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tmdt.turf.dto.request.LoginDto;
import tmdt.turf.dto.request.RegisterDto;
import tmdt.turf.dto.request.TokenDto;
import tmdt.turf.service.authentication.AuthService;
import tmdt.turf.service.user.UserService;
import tmdt.turf.util.APIResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createNewAccount(@RequestBody @Valid RegisterDto registerDto) {
        authService.createNewAccount(registerDto);
        return new ResponseEntity<>(
                new APIResponse("Create account successfully.", null),
                HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {
        TokenDto token = authService.login(loginDto);
        return new ResponseEntity<>(
                new APIResponse("Login successfully.", token),
                HttpStatus.OK
        );
    }
}
