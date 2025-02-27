package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.Validation.product.NotExistColor;
import com.example.projectc1023i1.Validation.product.NotExistProduct;
import com.example.projectc1023i1.Validation.product.NotExistSize;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindPVariantDTO {
    @NotExistProduct
    @NotNull(message = "Không tồn tại product")
    private Integer productId;
    @NotExistSize
    @NotNull(message = "Bạn chưa chọn size")
    private Integer sizeId;
    @NotExistColor
    @NotNull(message = "Bạn chưa chọn color")
    private Integer colorId;
}
