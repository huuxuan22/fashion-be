package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepo extends JpaRepository<Image, Integer> {
    @Query("select i.imageUrl from Image as i where i.product.productId = :param")
    List<String> findByProductId(@Param("param") Integer productId);

    @Query("select i from Image i where i.product.productId = :productId")
    List<Image> findAllByProductId(@Param("productId") int productId);
}
