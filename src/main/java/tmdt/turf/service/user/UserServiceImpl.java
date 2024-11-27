package tmdt.turf.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tmdt.turf.exception.CustomException;
import tmdt.turf.model.enums.Role;
import tmdt.turf.model.user.User;
import tmdt.turf.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    final private UserRepository userRepository;

    @Override
    public User loadUser(Integer userId, String email, Role role) throws UsernameNotFoundException {
        return User.builder()
                .id(userId)
                .email(email)
                .role(role)
                .build();
    }

    @Override
    public User getProfile() {
        User secureUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(secureUser.getId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
    }
}
