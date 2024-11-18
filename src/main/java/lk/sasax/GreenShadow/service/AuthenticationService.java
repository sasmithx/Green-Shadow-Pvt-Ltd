package lk.sasax.GreenShadow.service;



import lk.sasax.GreenShadow.auth.request.SignInRequest;
import lk.sasax.GreenShadow.auth.request.SignUpRequest;
import lk.sasax.GreenShadow.auth.response.JWTAuthResponse;
import lk.sasax.GreenShadow.dto.impl.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthenticationService {
    JWTAuthResponse signIn(SignInRequest sIgnInRequest);
    JWTAuthResponse signUp(SignUpRequest signUpRequest);

   // List<String> sendWishes();

    Boolean checkCredentials(UserDTO userDTO);
}
