package com.example.projectc1023i1.request;

import com.example.projectc1023i1.Validation.user.NotExistUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest implements Validator {
    @NotBlank(message = "*Username không được để trống ")
    @NotExistUsername
    private String username;
    @NotBlank(message = "*Mật khẩu không được để trống")
    private String password;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
