package lk.sasax.GreenShadow.customObj.impl;

import lk.sasax.GreenShadow.customObj.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserErrorResponse implements UserResponse, Serializable {
    private int errorCode;
    private String errorMessage;
}
