package com.example.projectc1023i1.respone.errorsValidate;

import com.example.projectc1023i1.Dto.ListCharacter;
import com.example.projectc1023i1.Validation.Categories.NotExistCategory;
import com.example.projectc1023i1.Validation.product.ProductExist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductErrorsRespone {
    private String productName;
    private String description;
    private String characters;
    private String thumbnail;
    private String price;
    private String sellPrice;
    private String subCategories;
}
