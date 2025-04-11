package com.example.projectc1023i1.Validation.Categories;

import com.example.projectc1023i1.repository.impl.ICategoriesRepo;
import com.example.projectc1023i1.repository.impl.ISubCategories;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotExistSubCateValidator implements ConstraintValidator<NotExistSubCateId,Integer> {
    @Autowired
    private ISubCategories subCategories;

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return subCategories.existsById(Long.valueOf(integer));
    }
}
