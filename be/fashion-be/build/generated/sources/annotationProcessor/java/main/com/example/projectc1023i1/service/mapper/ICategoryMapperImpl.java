package com.example.projectc1023i1.service.mapper;

import com.example.projectc1023i1.Dto.get_data.category_mapstruck.CategoryMapper;
import com.example.projectc1023i1.Dto.get_data.category_mapstruck.SubCategoryMapper;
import com.example.projectc1023i1.model.Categories;
import com.example.projectc1023i1.model.SubCategories;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T14:48:29+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ICategoryMapperImpl implements ICategoryMapper {

    @Override
    public CategoryMapper toDto(Categories categories) {
        if ( categories == null ) {
            return null;
        }

        CategoryMapper.CategoryMapperBuilder categoryMapper = CategoryMapper.builder();

        categoryMapper.categories( toSubCategoryDtoList( categories.getSubCategories() ) );
        categoryMapper.categorieId( categories.getCategorieId() );
        categoryMapper.categoriesName( categories.getCategoriesName() );
        categoryMapper.description( categories.getDescription() );
        categoryMapper.thumbnail( categories.getThumbnail() );

        return categoryMapper.build();
    }

    @Override
    public Categories toEntity(CategoryMapper categoryMapper) {
        if ( categoryMapper == null ) {
            return null;
        }

        Categories.CategoriesBuilder categories = Categories.builder();

        categories.subCategories( toSubCategoryEntityList( categoryMapper.getCategories() ) );
        categories.categorieId( categoryMapper.getCategorieId() );
        categories.categoriesName( categoryMapper.getCategoriesName() );
        categories.description( categoryMapper.getDescription() );
        categories.thumbnail( categoryMapper.getThumbnail() );

        return categories.build();
    }

    @Override
    public SubCategoryMapper toSubCategoryDto(SubCategories subCategory) {
        if ( subCategory == null ) {
            return null;
        }

        SubCategoryMapper.SubCategoryMapperBuilder subCategoryMapper = SubCategoryMapper.builder();

        subCategoryMapper.subCategoryId( subCategory.getSubCategoryId() );
        subCategoryMapper.subCategoryName( subCategory.getSubCategoryName() );

        return subCategoryMapper.build();
    }

    @Override
    public SubCategories toSubCategoryEntity(SubCategoryMapper subCategoryMapper) {
        if ( subCategoryMapper == null ) {
            return null;
        }

        SubCategories.SubCategoriesBuilder subCategories = SubCategories.builder();

        subCategories.subCategoryId( subCategoryMapper.getSubCategoryId() );
        subCategories.subCategoryName( subCategoryMapper.getSubCategoryName() );

        return subCategories.build();
    }

    @Override
    public List<CategoryMapper> toDtoList(List<Categories> categoriesList) {
        if ( categoriesList == null ) {
            return null;
        }

        List<CategoryMapper> list = new ArrayList<CategoryMapper>( categoriesList.size() );
        for ( Categories categories : categoriesList ) {
            list.add( toDto( categories ) );
        }

        return list;
    }

    @Override
    public List<Categories> toEntityList(List<CategoryMapper> categoryMapperList) {
        if ( categoryMapperList == null ) {
            return null;
        }

        List<Categories> list = new ArrayList<Categories>( categoryMapperList.size() );
        for ( CategoryMapper categoryMapper : categoryMapperList ) {
            list.add( toEntity( categoryMapper ) );
        }

        return list;
    }

    @Override
    public List<SubCategoryMapper> toSubCategoryDtoList(List<SubCategories> subCategoryList) {
        if ( subCategoryList == null ) {
            return null;
        }

        List<SubCategoryMapper> list = new ArrayList<SubCategoryMapper>( subCategoryList.size() );
        for ( SubCategories subCategories : subCategoryList ) {
            list.add( toSubCategoryDto( subCategories ) );
        }

        return list;
    }

    @Override
    public List<SubCategories> toSubCategoryEntityList(List<SubCategoryMapper> subCategoryMapperList) {
        if ( subCategoryMapperList == null ) {
            return null;
        }

        List<SubCategories> list = new ArrayList<SubCategories>( subCategoryMapperList.size() );
        for ( SubCategoryMapper subCategoryMapper : subCategoryMapperList ) {
            list.add( toSubCategoryEntity( subCategoryMapper ) );
        }

        return list;
    }
}
