package com.example.projectc1023i1.respone.errorsValidate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSubCateErrors {
    private String subCategoryName;
    private String categoriesId;
}
