package com.example.projectc1023i1.Validation.user;

import com.example.projectc1023i1.repository.impl.ISubCategories;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistSubCateNameValidator implements ConstraintValidator<ExistSubCateName, String> {
    @Autowired
    private ISubCategories subCategoriesRepository;

    @Override
    public void initialize(ExistSubCateName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
