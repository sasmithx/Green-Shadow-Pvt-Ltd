package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.dto.impl.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void save(UserDTO userDTO);
}
