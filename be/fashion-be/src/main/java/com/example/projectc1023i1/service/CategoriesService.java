package com.example.projectc1023i1.service;

import com.example.projectc1023i1.Dto.CategoriesDTO;
import com.example.projectc1023i1.Dto.get_data.category_mapstruck.CategoryMapper;
import com.example.projectc1023i1.Exception.DataNotFoundException;
import com.example.projectc1023i1.model.Categories;
import com.example.projectc1023i1.repository.impl.ICategoriesRepo;
import com.example.projectc1023i1.service.impl.ICategoriesService;
import com.example.projectc1023i1.service.mapper.ICategoryMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService implements ICategoriesService {
    @Autowired
    private ICategoriesRepo categoriesRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ICategoryMapper categoryMapper;


    @Override
    public List<CategoryMapper> findAll() {
       List<Categories> categories = categoriesRepo.findAll();
       List<CategoryMapper> categoryMappers = new ArrayList<>();
       for (Categories c : categories) {
           categoryMappers.add(categoryMapper.toDto(c));
       }
        return categoryMappers;
    }

    @Override
    public Categories save(Categories categories) {
        return categoriesRepo.save(categories);
    }

    @Override
    public void delete(Categories categories) {
        categoriesRepo.delete(categories);
    }

    @Override
    public Optional<Categories> findById(Integer id) {
        Categories categories = categoriesRepo.findById(id).orElseThrow(() -> new DataNotFoundException("không tìm thấy loại sản phẩm"));
        return categoriesRepo.findById(id);
    }

    @Override
    public Categories findByName(String name) {
        return null;
    }

    @Override
    public Categories convertToCategories(CategoriesDTO categoriesDTO) {
        return modelMapper.map(categoriesDTO, Categories.class);
    }
}
