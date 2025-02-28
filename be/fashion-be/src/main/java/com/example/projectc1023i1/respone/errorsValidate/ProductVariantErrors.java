package com.example.projectc1023i1.respone.errorsValidate;

import com.example.projectc1023i1.Validation.product.NotExistColor;
import com.example.projectc1023i1.Validation.product.NotExistProduct;
import com.example.projectc1023i1.Validation.product.NotExistSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantErrors {
    private String productVariantId;
    private String price;
    private String stock;
    private String productId;
    private String colorId;
    private String sizeId;
}
