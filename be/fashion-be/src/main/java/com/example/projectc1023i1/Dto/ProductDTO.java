package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.Validation.Categories.NotExistCategory;
import com.example.projectc1023i1.Validation.Categories.NotExistSubCateId;
import com.example.projectc1023i1.Validation.product.ProductExist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không vượt quá 255 ký tự")
    @ProductExist(message = "Tên sản phẩm đã tồn tại")
    private String productName;

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    private String description;

    @NotNull(message = "Thông số sản phẩm không được để trống")
    @Size(min = 1, message = "Phải có ít nhất 1 thông số sản phẩm")
        private List<@Valid ListCharacter> characters;


    @NotNull(message = "Giá gốc không được để trống")
    @Min(value = 0, message = "Giá gốc không được âm")
    @Max(value = 100000000, message = "Giá gốc không vượt quá 100,000,000")
    private Integer price;

    @NotNull(message = "Giá bán không được để trống")
    @Min(value = 0, message = "Giá bán không được âm")
    @Max(value = 100000000, message = "Giá bán không vượt quá 100,000,000")
    private Integer sellPrice;

    @NotNull(message = "Danh mục phụ không được để trống")
    @NotExistSubCateId(message = "Danh mục phụ không tồn tại")
    private Integer subCategories;

    @Builder.Default
    private Boolean isActive = true;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductDTO productDTO = (ProductDTO) target;
        // Kiểm tra sellPrice không lớn hơn price
        if (productDTO.getPrice() != null && productDTO.getSellPrice() != null
                && productDTO.getSellPrice() > productDTO.getPrice()) {
            errors.rejectValue("sellPrice", "sellPrice.invalid",
                    "Giá bán không được lớn hơn giá gốc");
        }
    }
    public Integer getTotalQuantity() {
        if (characters == null || characters.isEmpty()) {
            return 0;
        }
        return characters.stream()
                .mapToInt(ListCharacter::getQuality)
                .sum();
    }
}