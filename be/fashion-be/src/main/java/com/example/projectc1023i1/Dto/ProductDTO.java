package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.Validation.Categories.NotExistCategory;
import com.example.projectc1023i1.Validation.product.ProductExist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO implements Validator {
    @ProductExist
    @NotBlank(message = "Chưa điền tên của sản phẩm")
    @Size(max = 100,message = "không nhập quá 100 tù")
    private String productName;
    @NotBlank(message = "Bạn chưa nhập mô tả")
    @Size(max = 500, message = "Không được nhập quá 500 từ")
    private String description;
    
    @NotNull(message = "Bạn chưa chọn category")
    @NotExistCategory
    private Integer categories;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }

}
