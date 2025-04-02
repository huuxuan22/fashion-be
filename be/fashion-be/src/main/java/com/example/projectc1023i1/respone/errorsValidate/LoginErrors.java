package com.example.projectc1023i1.respone.errorsValidate;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginErrors {
    private String username;
    private String password;
}
