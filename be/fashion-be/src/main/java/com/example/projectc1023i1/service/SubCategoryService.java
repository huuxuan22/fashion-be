package com.example.projectc1023i1.service;

import com.example.projectc1023i1.model.SubCategories;
import com.example.projectc1023i1.repository.impl.ISubCategories;
import com.example.projectc1023i1.service.impl.ISubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubCategoryService implements ISubCategoryService {
    @Autowired
    private ISubCategories subCategoriesRepository;

    @Override
    @Transactional
    @Modifying
    public void saveSubCategory(SubCategories subCategories) {
        subCategoriesRepository.save(subCategories);
    }

    @Override
    public Boolean deleteSubCategory(SubCategories subCategories) {
        subCategoriesRepository.delete(subCategories);
        return true;
    }

    @Override
    public SubCategories getSubCategory(SubCategories subCategories) {
        return null;
    }

    @Override
    public List<SubCategories> getAllSubCategories() {
        return List.of();
    }

    @Override
    public List<SubCategories> findByCategoryId(Long categoryId) {
        return  subCategoriesRepository.findAllByCategoriesId(categoryId);
    }
}
