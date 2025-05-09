package com.example.projectc1023i1.Validation.employee;

import com.example.projectc1023i1.Validation.user.NotExistUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistUsernameUserValidator.class)
public @interface ExistUsernameUser {
    String message() default "*Tài khoản nhân viên này đã tồn tại ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
