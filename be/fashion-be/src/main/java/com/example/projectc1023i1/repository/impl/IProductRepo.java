package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepo extends JpaRepository<Product, Integer> {
    @Query("select p from Product as p where p.isActive = true order by p.createdAt desc ")
     Page<Product> getAllActiveProduct(Pageable pageable);
    @Query("select p from Product as p where p.productName = :param")
    Optional<Product> findByProductName(@Param("param") String productName);
    @Modifying
    @Transactional
    @Query("update Product as p set p.isActive = false where p.productId = :param")
    void deleteProduct(@Param("param") Integer productId);
    @Query("select max(p.productId) from Product as p")
    Integer findIdMax();
    @Modifying
    @Transactional
    @Query("update Product p set p.thumbnail = :param2 where p.productId = :param1")
    void setMainImage(@Param("param2") String param2, @Param("param1") Integer param1);

    @Query("SELECT p.productName FROM Product p WHERE p.productName LIKE CONCAT('%', :param, '%')")
    List<String> searchProducts(@Param("param") String param);

}
