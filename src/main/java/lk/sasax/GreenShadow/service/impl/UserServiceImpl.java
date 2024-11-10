package lk.sasax.GreenShadow.service.impl;

import jdk.jshell.spi.ExecutionControl;
import lk.sasax.GreenShadow.dto.impl.UserDTO;
import lk.sasax.GreenShadow.entity.impl.User;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.repository.UserRepository;
import lk.sasax.GreenShadow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public void save(UserDTO userDTO) {
        User savedUser =
            userRepository.save(modelMapper.map(userDTO, User.class));
        if(savedUser == null){
            throw new DataPersistFailedException("User not saved");
        }
    }
}
