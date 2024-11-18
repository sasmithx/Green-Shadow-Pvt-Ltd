package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.auth.request.SignInRequest;
import lk.sasax.GreenShadow.auth.request.SignUpRequest;
import lk.sasax.GreenShadow.auth.response.JWTAuthResponse;
import lk.sasax.GreenShadow.dto.impl.UserDTO;
import lk.sasax.GreenShadow.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<JWTAuthResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        System.out.println("signUp");
        System.out.println(signUpRequest);
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

   /* @GetMapping("/send_wishes")
    public List<String> sendWishes(){
        return authenticationService.sendWishes();
    }*/

    @PostMapping("/check_credentials")
    public Boolean checkCredentials(@RequestBody UserDTO userDTO){
        System.out.println(userDTO.getEmail() + " " + userDTO.getPassword() + " " + userDTO.getRole());
        return authenticationService.checkCredentials(userDTO);
    }
}
