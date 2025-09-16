package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.SubCategoriesDTO;
import com.example.projectc1023i1.Dto.SubCategoryDTO;
import com.example.projectc1023i1.model.SubCategories;
import com.example.projectc1023i1.repository.impl.ICategoriesRepo;
import com.example.projectc1023i1.repository.impl.ISubCategories;
import com.example.projectc1023i1.service.impl.ISubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubCategoryService implements ISubCategoryService {
    @Autowired
    private ISubCategories subCategoriesRepository;
    @Autowired
    private ICategoriesRepo categoriesRepository;
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
    public Page<SubCategories> getAllSubCategories(Pageable pageable) {
        return subCategoriesRepository.findAll(pageable);
    }

    @Override
    public List<SubCategories> findByCategoryId(Long categoryId) {
        return  subCategoriesRepository.findAllByCategoriesId(categoryId);
    }

    @Override
    public void addSubCategory(SubCategoryDTO subCategories) {
        subCategoriesRepository.save(SubCategories.builder()
                        .categories(categoriesRepository.findById(subCategories.getCategoryId()).get())
                        .subCategoryName(subCategories.getSubCategoryName())
                .build());
    }

    @Override
    public void deleteSubCategories(Long subCategoryId) {
        subCategoriesRepository.deleteById(subCategoryId);
    }
}
