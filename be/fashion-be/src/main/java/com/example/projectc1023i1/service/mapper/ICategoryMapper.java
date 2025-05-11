package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.category_mapstruck.CategoryMapper;
import com.example.projectc1023i1.Dto.get_data.category_mapstruck.SubCategoryMapper;
import com.example.projectc1023i1.model.Categories;
import com.example.projectc1023i1.model.SubCategories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {

    // Map từ entity sang DTO
    @Mapping(source = "subCategories", target = "categories")
    CategoryMapper toDto(Categories categories);

    // Map từ DTO sang entity (nếu cần)
    @Mapping(source = "categories", target = "subCategories")
    Categories toEntity(CategoryMapper categoryMapper);

    // Map cho SubCategory
    SubCategoryMapper toSubCategoryDto(SubCategories subCategory);

    SubCategories toSubCategoryEntity(SubCategoryMapper subCategoryMapper);

    // Danh sách
    List<CategoryMapper> toDtoList(List<Categories> categoriesList);

    List<Categories> toEntityList(List<CategoryMapper> categoryMapperList);

    List<SubCategoryMapper> toSubCategoryDtoList(List<SubCategories> subCategoryList);

    List<SubCategories> toSubCategoryEntityList(List<SubCategoryMapper> subCategoryMapperList);
}
