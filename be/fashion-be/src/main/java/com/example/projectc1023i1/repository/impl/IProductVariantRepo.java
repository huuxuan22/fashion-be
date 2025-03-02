package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.ProductVariant;
import com.example.projectc1023i1.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductVariantRepo extends JpaRepository<ProductVariant, Integer> {
    @Query(value = "select c.* \n" +
            "from product_variants as pv\n" +
            "inner join color as c on c.color_id = pv.color_id\n" +
            " where pv.product_id = 1 and pv.size_id = 2 ;", nativeQuery = true)
    List<Color> findByProducIdAndSizeId(
            @Param("productId") Integer productId,
            @Param("sizeId") Integer sizeId);

    @Query(value = "select * from product_variants " +
            "where product_id = :productId " +
            "and size_id = :sizeId " +
            "and color_id = :colorId",
            nativeQuery = true )
    ProductVariant findQualytyByProductNameAndSizeIdAndColorId(
            @Param("productId") Integer productId,
            @Param("sizeId") Integer sizeId,
            @Param("colorId") Integer colorId
    );

    @Query("SELECT p FROM ProductVariant p WHERE p.product.productId = :productId")
    Page<ProductVariant> findByProductId(@Param("productId") Integer productId, Pageable pageable);

}
