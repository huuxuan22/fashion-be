package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.Validation.Categories.NotExistCategory;
import com.example.projectc1023i1.Validation.product.NotExistProduct;
import com.example.projectc1023i1.Validation.product.ProductExist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpateDTO implements Validator {
    @NotExistProduct
    private Integer productId;

    @ProductExist
    @Size(max = 100,message = "không nhập quá 100 tù")
    private String productName;
    @NotBlank(message = "Khong duoc de trong")
    private String description;
    @NotExistCategory
    private Integer categories;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
