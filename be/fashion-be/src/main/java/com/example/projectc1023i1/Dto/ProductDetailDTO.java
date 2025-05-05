package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.Size;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.lang.annotation.Annotation;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// danh sach san pham khi order cua 1 nguoi tren he thong
public class ProductDetailDTO  {
    private Color color;
    private Size size;
    private Product product;
    private Integer stock;
}
