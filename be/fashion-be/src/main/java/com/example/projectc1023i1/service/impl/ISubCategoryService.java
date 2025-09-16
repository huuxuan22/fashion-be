package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.SubCategoriesDTO;
import com.example.projectc1023i1.Dto.SubCategoryDTO;
import com.example.projectc1023i1.model.SubCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISubCategoryService {
    void saveSubCategory(SubCategories subCategories);
    Boolean deleteSubCategory(SubCategories subCategories);
    SubCategories getSubCategory(SubCategories subCategories);
    Page<SubCategories> getAllSubCategories(Pageable pageable);
    List<SubCategories> findByCategoryId(Long categoryId);
    void addSubCategory(SubCategoryDTO categoriesDTO);
    void deleteSubCategories(@Param("id") Long subCategoryId);

}
