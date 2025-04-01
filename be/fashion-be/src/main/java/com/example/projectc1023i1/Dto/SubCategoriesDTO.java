package com.example.projectc1023i1.Dto;

import com.example.projectc1023i1.Validation.Categories.NotExistSubCateId;
import com.example.projectc1023i1.model.Categories;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoriesDTO {
    @NotBlank(message = "Tên danh mục con không được để trống")
    @Size(min = 2, max = 100, message = "Tên danh mục con phải từ 2 đến 100 ký tự")
    private String subCategoryName;

    @NotNull(message = "Danh mục cha không được để trống")
    @NotExistSubCateId
    private Integer categoriesId;

}
