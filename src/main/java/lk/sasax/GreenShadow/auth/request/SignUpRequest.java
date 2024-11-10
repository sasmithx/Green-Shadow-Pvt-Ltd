package lk.sasax.GreenShadow.auth.request;

import lk.sasax.GreenShadow.util.Enum.AccessRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpRequest {
    private String email;
    private String password;
    private AccessRole role;
}
