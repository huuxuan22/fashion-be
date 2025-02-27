package com.example.projectc1023i1.service.impl;

import com.example.projectc1023i1.Dto.ProductMorphology;
import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.ProductVariant;

import java.util.List;

public interface IProductVariantService {
    List<ProductVariant> getAllProductVariants();
    ProductVariant getProductVariant(Integer id);
    ProductVariant addProductVariant(ProductMorphology productMorphology);
    ProductVariant updateProductVariant(ProductVariant productVariant);
    List<Color> findByProductIdAndSizeId(Integer productId, Integer sizeId);
    ProductVariant findByProductIdAndColorIdAndSizeId(Integer productId, Integer colorId, Integer sizeId);
    void deleteProductVariant(Integer id);
}
