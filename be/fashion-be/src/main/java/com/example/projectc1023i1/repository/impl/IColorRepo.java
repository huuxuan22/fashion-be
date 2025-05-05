package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Color;
import com.example.projectc1023i1.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IColorRepo extends JpaRepository<Color, Integer> {
    @Query("select c from Color c " +
            "inner join ProductVariant  pv on pv.color.colorId = c.colorId " +
            "where pv.product.productId = :productId")
    List<Color> getAllColorOfOneProduct(@Param("productId") int productId);
}
