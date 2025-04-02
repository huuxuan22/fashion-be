package com.example.projectc1023i1.respone.errorsValidate;

import com.example.projectc1023i1.Validation.employee.ExistEmailUser;
import com.example.projectc1023i1.Validation.employee.ExistNumberphoneUser;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterErrors {
    private String gender;
    private String numberphone;
    private String fullName;
    private String birthday;
    private String username;
    private String password;
    private String email;
}
