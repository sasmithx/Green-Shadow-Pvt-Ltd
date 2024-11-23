package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;
    //private final ModelMapper mapper;

   /* @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            return userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        };
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow();
    }

    /*@Override
    public void save(UserDTO userDTO) {
        userRepo.save(mapper.map(userDTO, User.class));
    }*/
}
