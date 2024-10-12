package tmdt.turf.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tmdt.turf.model.enums.Role;
import tmdt.turf.model.user.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Override
    public User loadUser(Integer userId, String email, Role role) throws UsernameNotFoundException {
        return User.builder()
                .id(userId)
                .email(email)
                .role(role)
                .build();
    }
}
