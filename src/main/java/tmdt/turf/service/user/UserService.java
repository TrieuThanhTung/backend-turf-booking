package tmdt.turf.service.user;

import tmdt.turf.model.enums.Role;
import tmdt.turf.model.user.User;

public interface UserService {
    User loadUser(Integer userId, String email, Role role);

    User getProfile();
}
