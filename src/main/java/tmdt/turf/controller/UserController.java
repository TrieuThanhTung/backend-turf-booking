package tmdt.turf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tmdt.turf.model.user.User;
import tmdt.turf.service.user.UserService;
import tmdt.turf.util.APIResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @GetMapping
    public ResponseEntity<?> getProfile() {
        User user =  userService.getProfile();
        return ResponseEntity.ok(new APIResponse("Get profile successfully!", user));
    }
}
