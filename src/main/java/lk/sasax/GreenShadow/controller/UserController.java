package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.auth.request.SignInRequest;
import lk.sasax.GreenShadow.auth.request.SignUpRequest;
import lk.sasax.GreenShadow.auth.response.JWTAuthResponse;
import lk.sasax.GreenShadow.dto.impl.UserDTO;
import lk.sasax.GreenShadow.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignInRequest signInRequest){
        logger.info("SignIn request: {}", signInRequest);
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<JWTAuthResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        logger.info("SignUp request: {}", signUpRequest);
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/check_credentials")
    public Boolean checkCredentials(@RequestBody UserDTO userDTO){
        System.out.println(userDTO.getEmail() + " " + userDTO.getPassword() + " " + userDTO.getRole());
        logger.info("Received request to check credentials for user: {}", userDTO.getEmail());
        return authenticationService.checkCredentials(userDTO);
    }
}
