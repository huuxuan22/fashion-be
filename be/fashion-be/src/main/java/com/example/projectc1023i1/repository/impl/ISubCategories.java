package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.SubCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISubCategories extends JpaRepository<SubCategories,Long> {
    @Query(value = "select s from SubCategories s where s.categories.categorieId = :categoryId")
    List<SubCategories> findAllByCategoriesId(@Param("categoryId") Long subCategoryId);
}
