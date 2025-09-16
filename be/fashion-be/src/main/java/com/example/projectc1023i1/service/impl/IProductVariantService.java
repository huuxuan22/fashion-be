package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.AddQualityProduct;
import com.example.projectc1023i1.Dto.ProductMorphology;
import com.example.projectc1023i1.Dto.ProductVariantDTO;
import com.example.projectc1023i1.Dto.get_data.CountByQualitySize;
import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductVariantService {
    List<ProductVariant> getAllProductVariants();
    ProductVariant getProductVariant(Integer id);
    ProductVariant addProductVariant(ProductMorphology productMorphology);
    ProductVariant updateProductVariant(ProductVariant productVariant);
    List<Color> findByProductIdAndSizeId(Integer productId, Integer sizeId);
    ProductVariant findByProductIdAndColorIdAndSizeId(Integer productId, Integer colorId, Integer sizeId);
    void deleteProductVariant(Integer id);
    ProductVariant UpdateProductVariant(ProductVariantDTO productVariantDTO);
    Page<ProductVariant> findByProductId(Integer productId, Pageable pageable);
    Integer getSoldOfProduct(Integer productId);
    List<CountByQualitySize> countQuanlityWithSizeByColorId(Integer productId, Integer colorId);
    void addQuality(List<AddQualityProduct> addQualityProducts, Integer productId);
}
