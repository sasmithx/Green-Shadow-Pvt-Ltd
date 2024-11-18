package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
    void save(UserDTO userDTO);
}
