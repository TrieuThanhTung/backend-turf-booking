package tmdt.turf.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tmdt.turf.model.enums.Role;
import tmdt.turf.model.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl {

    public UserDetails loadUser(Integer userId, String email, Role role) throws UsernameNotFoundException {
        return User.builder()
                .id(userId)
                .email(email)
                .role(role)
                .build();
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Role role) {
        List<String> roles = new ArrayList<>();
        roles.add(role.name());
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
