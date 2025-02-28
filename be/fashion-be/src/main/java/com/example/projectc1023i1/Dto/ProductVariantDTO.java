package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.Validation.product.NotExistColor;
import com.example.projectc1023i1.Validation.product.NotExistProduct;
import com.example.projectc1023i1.Validation.product.NotExistProductVariant;
import com.example.projectc1023i1.Validation.product.NotExistSize;
import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Size;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantDTO implements Validator {
    @NotExistProductVariant
    @NotNull(message = "Bạn chưa chọn sản phẩm")
    private Integer productVariantId;
    @NotNull(message = "Bạn chưa chọn giá cho sản phẩm")
    @Min(value = 0,message = "Giá không được nhỏ hơn 0")
    private Double price;
    @NotNull(message = "Bạn chưa nhập số lượng cho sản phẩm")
    private Integer stock;
    @NotExistProduct
    @NotNull(message = "Bạn chưa chọn sản phẩm")
    private Integer productId;
    @NotNull(message = "Bạn chưa chọn màu sắc")
    @NotExistColor
    private Integer colorId;
    @NotExistSize
    @NotNull(message = "Bạn chưa chọn kích thước")
    private Integer sizeId;
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
