package com.example.projectc1023i1.respone.errorsValidate;

import com.example.projectc1023i1.Validation.product.NotExistColor;
import com.example.projectc1023i1.Validation.product.NotExistSize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMorphologyErrors {
    private String productId;
    private String colorId;
    private String sizeId;
    private String price;
    private String stock;
}
