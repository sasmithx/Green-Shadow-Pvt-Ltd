package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.auth.request.SignInRequest;
import lk.sasax.GreenShadow.auth.request.SignUpRequest;
import lk.sasax.GreenShadow.auth.response.JWTAuthResponse;
import lk.sasax.GreenShadow.dto.impl.UserDTO;
import lk.sasax.GreenShadow.entity.impl.Staff;
import lk.sasax.GreenShadow.entity.impl.User;
import lk.sasax.GreenShadow.exception.DuplicateRecordException;
import lk.sasax.GreenShadow.exception.InvalidAccessRoleException;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.repository.StaffRepository;
import lk.sasax.GreenShadow.repository.UserRepository;
import lk.sasax.GreenShadow.service.AuthenticationService;
import lk.sasax.GreenShadow.service.JWTService;
import lk.sasax.GreenShadow.util.Enum.AccessRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final ModelMapper mapper;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    //private final CustomerRepo customerRepo;
    private final Path stateFilePath = Paths.get("task_state.txt");
    //private final EmployeeRepo employeeRepo;
    private final StaffRepository staffRepo;

    @Override
    public JWTAuthResponse signIn(SignInRequest signInRequest) {
        log.info("signing in new user: {}", signInRequest.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        User user = userRepo.findByEmail(signInRequest.getEmail()).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        String generatedToken = jwtService.generateToken(user);
        String role = user.getRole().name();

        return JWTAuthResponse.builder().token(generatedToken).role(role).build();
    }

    @Override
    public JWTAuthResponse signUp(SignUpRequest signUpRequest) {
        System.out.println(signUpRequest);
        log.info("trying to create a new user account: {}", signUpRequest.getEmail());

        User savedUser;
        if(userRepo.existsById(signUpRequest.getEmail())){
            throw new DuplicateRecordException("User with email " + signUpRequest.getEmail() + " already exists");
        }else{
            Optional<Staff> staff = staffRepo.findByEmail(signUpRequest.getEmail());

            if (staff.isEmpty()){
                throw new NotFoundException("Staff with email " + signUpRequest.getEmail() + " not found.");
            }else{
                UserDTO userDTO = UserDTO.builder()
                        .email(signUpRequest.getEmail())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .role(AccessRole.valueOf(staff.get().getRole()))
                        .build();
                savedUser = userRepo.save(mapper.map(userDTO, User.class));
            }
        }
        String generatedToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public Boolean checkCredentials(UserDTO userDTO) {
        Optional<User> user = userRepo.findByEmail(userDTO.getEmail());
        System.out.println(user);

        if(user.isEmpty()){
            throw new NotFoundException("MANAGER email not found.");
        }else{
            System.out.println(user.get().getRole());

            if(user.get().getRole() == AccessRole.MANAGER){
                if(passwordEncoder.matches(userDTO.getPassword(), user.get().getPassword())){
                    return true;
                }else{
                    return false;
                }
            }else{
                throw new InvalidAccessRoleException("You don't have permission for this action.\nOnly managers are allowed.");
            }
        }
    }

    private boolean isTaskExecutedToday() {
        try {
            if (Files.exists(stateFilePath)) {
                System.out.println("exists");
                String lastExecutionDateStr = Files.readString(stateFilePath).trim();
                if(lastExecutionDateStr.isEmpty() || !lastExecutionDateStr.matches("-?\\d+")) {
                    return false;
                }
                Date lastExecutionDate = new Date(Long.parseLong(lastExecutionDateStr));
                Calendar currentCal = Calendar.getInstance();
                Calendar lastExecutionCal = Calendar.getInstance();
                lastExecutionCal.setTime(lastExecutionDate);
                return currentCal.get(Calendar.YEAR) == lastExecutionCal.get(Calendar.YEAR) &&
                        currentCal.get(Calendar.DAY_OF_YEAR) == lastExecutionCal.get(Calendar.DAY_OF_YEAR);
            }else {
                System.out.println("doesn't exist");
                Files.createFile(stateFilePath);
                Calendar yesterdayCal = Calendar.getInstance();
                yesterdayCal.add(Calendar.DAY_OF_MONTH, -1);
                Date yesterdayDate = yesterdayCal.getTime();
                saveLastExecutionDate(yesterdayDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveLastExecutionDate(Date executionDate) {
        try {
            Files.writeString(stateFilePath, String.valueOf(executionDate.getTime()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
