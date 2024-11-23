package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.impl.ReqResp;
import lk.sasax.GreenShadow.entity.impl.User;
import lk.sasax.GreenShadow.repository.UserRepository;
import lk.sasax.GreenShadow.service.AuthenticationService;
import lk.sasax.GreenShadow.util.Enum.AccessRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqResp signUp(ReqResp registrationRequest){
        ReqResp resp = new ReqResp();
        try {
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(AccessRole.valueOf(registrationRequest.getRole()));
            User UserResult = userRepository.save( user);
            if (UserResult != null &&
                    UserResult.getEmail() != null && !UserResult.getEmail().isEmpty() &&
                    UserResult.getUsername() != null && !UserResult.getUsername().isEmpty() &&
                    UserResult.getPassword() != null && !UserResult.getPassword().isEmpty()) {
                resp.setOurUsers(UserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqResp signIn(ReqResp signinRequest){
        ReqResp response = new ReqResp();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
            var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtUtil.generateToken(user);
            var refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }



}
