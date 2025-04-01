package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.model.SubCategories;

import java.util.List;

public interface ISubCategoryService {
    void saveSubCategory(SubCategories subCategories);
    Boolean deleteSubCategory(SubCategories subCategories);
    SubCategories getSubCategory(SubCategories subCategories);
    List<SubCategories> getAllSubCategories();
}
