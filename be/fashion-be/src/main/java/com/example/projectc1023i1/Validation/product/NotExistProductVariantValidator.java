package com.example.projectc1023i1.Validation.product;

import com.example.projectc1023i1.repository.impl.IProductVariantRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotExistProductVariantValidator implements ConstraintValidator<NotExistProductVariant, Integer> {
    @Autowired
    private IProductVariantRepo productVariantRepo;

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (productVariantRepo.existsById(integer)) {
            return true;
        }
        return false;
    }
}
