package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Product;
import com.example.projectc1023i1.model.ProductVariant;
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
    @Query("select p from Product as p where p.isActive = true and p.productName like  concat('%',:search,'%') order by p.createdAt desc ")
     Page<Product> getAllActiveProduct(Pageable pageable,@Param("search") String search);
    @Query(value = "SELECT p.* FROM products AS p " +
            "INNER JOIN sub_categories AS sc ON sc.sub_cate_id = p.sub_cate_id " +
            "INNER JOIN categories AS c ON c.categories_id = sc.categories_id " +
            "WHERE c.categories_id = :categoryId AND p.product_name LIKE CONCAT('%', :param, '%') " +
            "ORDER BY p.created_at DESC",
            countQuery = "SELECT COUNT(*) FROM products AS p " +
                    "INNER JOIN sub_categories AS sc ON sc.sub_cate_id = p.sub_cate_id " +
                    "INNER JOIN categories AS c ON c.categories_id = sc.categories_id " +
                    "WHERE c.categories_id = :categoryId AND p.product_name LIKE CONCAT('%', :param, '%')",
            nativeQuery = true)
    Page<Product> findByProductNameAndCategories(
            @Param("param") String productName,
            @Param("categoryId") Integer categoryId,
            Pageable pageable
    );

    /**
     * lay sanr pham giam gia
     * @return
     */
    @Query("select p from Product p where p.isActive = true order by p.createdAt limit 3")
    List<Product> getDiscountProduct();

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
    @Query("select  count(p) from Product p")
    Integer countProduct();
    @Query("select count(*) from Feedback where product.productId = :productId")
    Integer getTotalPage(@Param("productId") Integer productId);
    @Query("select p from  Product p where p.productName like concat('%',:value,'%') ")
    Page<Product> findByName(@Param("value") String value,Pageable pageable);
    @Query("select count(p) from  Product p where p.productName like concat('%',:value,'%') ")
    Integer countAllByProductName(@Param("value") String productName);

    @Query("select p from Product p order by p.createdAt desc limit 10")
    List<Product> findAll10();

    @Query("select p from Product p where p.categories.subCategoryId = :subCateId and p.productId != :productId")
    List<Product> findAllByProductName(@Param("subCateId") Integer subCateId, @Param("productId") Integer productId);
    @Query("select p from Product p where p.productName like concat('%',:productName, '%') ")
    List<Product> findAllByProductName(@Param("productName") String productName);
    @Query("select p from  Product p order by p.price asc limit 12")
    List<Product> findAll12();
}
