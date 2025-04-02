package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.Validation.employee.ExistEmailUser;
import com.example.projectc1023i1.Validation.employee.ExistNumberphoneUser;
import com.example.projectc1023i1.Validation.employee.ExistUsernameUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Validator {
    @NotNull(message = "Giới tính không được để trống")
    private Boolean gender;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(\\+84|0)[35789][0-9]{8}$", message = "Số điện thoại không hợp lệ")
    @ExistNumberphoneUser
    private String numberphone;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 50, message = "Họ và tên phải có từ 2 đến 50 ký tự")
    private String fullName;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")  // Định dạng ngày tháng
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;


    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 5, max = 20, message = "Tên đăng nhập phải có từ 5 đến 20 ký tự")
    @ExistUsernameUser
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @ExistEmailUser
    private String email;


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
