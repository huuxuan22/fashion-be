package com.example.projectc1023i1.Validation.user;

import com.example.projectc1023i1.repository.impl.IUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotExistUsernameValidator implements ConstraintValidator<NotExistUsername, String> {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository.existsByUsername(string)) {
            return true;
        }
        return false;
    }
}
