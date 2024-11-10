package lk.sasax.GreenShadow.dto.impl;

import lk.sasax.GreenShadow.customObj.UserResponse;
import lk.sasax.GreenShadow.dto.SuperDTO;
import lk.sasax.GreenShadow.util.Enum.AccessRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO implements SuperDTO, UserResponse {
    private String email;
    private String password;
    private AccessRole role;
}
