package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.impl.UserDTO;
import lk.sasax.GreenShadow.entity.impl.User;
import lk.sasax.GreenShadow.repository.UserRepository;
import lk.sasax.GreenShadow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final ModelMapper mapper;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            return userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        };
    }

    @Override
    public void save(UserDTO userDTO) {
        userRepo.save(mapper.map(userDTO, User.class));
    }
}
