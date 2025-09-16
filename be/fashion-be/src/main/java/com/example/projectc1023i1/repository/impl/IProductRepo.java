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
    @Query("select p from Product p where p.isActive = true order by p.createdAt limit 4    ")
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


    @Query("select p from Product p where p.categories.categories.categorieId = 2 order by p.createdAt desc limit 10")
    List<Product> findAllNam10();

    @Query("select p from Product p where p.categories.categories.categorieId = 1 order by p.createdAt desc limit 10")
    List<Product> findAllNu10();

    @Query("select p from Product p where p.categories.subCategoryId = :subCateId and p.productId != :productId")
    List<Product> findAllByProductName(@Param("subCateId") Integer subCateId, @Param("productId") Integer productId);
    @Query("select p from Product p where p.productName like concat('%',:productName, '%') ")
    List<Product> findAllByProductName(@Param("productName") String productName);
    @Query("select p from  Product p order by p.price asc limit 12")
    List<Product> findAll12();

    @Query(value = "select \n" +
            "    product_id,\n" +
            "    product_name,\n" +
            "    description,\n" +
            "    sub_cate_id,\n" +
            "    quality,\n" +
            "    is_active,\n" +
            "    thumbnail,\n" +
            "    created_at,\n" +
            "    updated_at,\n" +
            "    sell_price,\n" +
            "    price\n" +
            "from (\n" +
            "    select \n" +
            "        p.*, \n" +
            "        row_number() over (partition by c.categories_id order by p.created_at desc) as rn\n" +
            "    from products p\n" +
            "    join sub_categories sc on p.sub_cate_id = sc.sub_cate_id\n" +
            "    join categories c on sc.categories_id = c.categories_id\n" +
            ") as ranked\n" +
            "where rn <= 2;\n", nativeQuery = true)
    List<Product> getProductWithCategories();

    @Query("select distinct  p from Product p inner join ProductVariant pv on pv.product.productId = p.productId " +
            "inner join fetch  Size s on s.sizeId = pv.size.sizeId " +
            "inner join fetch  Color c on c.colorId = pv.color.colorId " +
            "inner join fetch   SubCategories sc on sc.subCategoryId = p.categories.subCategoryId " +
            "inner join fetch  Categories  cg on cg.categorieId = sc.categories.categorieId " +
            "where (:colorId is null or c.colorId = :colorId) " +
            "and (:sizeId is null  or s.sizeId = :sizeId) " +
            "and (:categoryId is null or cg.categorieId = :categoryId) and " +
            "(:subCategoryId IS NULL OR sc.subCategoryId = :subCategoryId)")
    Page<Product> findProducts(
            @Param("colorId") Integer colorId,
            @Param("sizeId") Integer sizeId,
            @Param("categoryId") Integer categoryId,
            @Param("subCategoryId") Integer subCategoryId,
            Pageable pageable);

    @Query("select  count(distinct  p.productId) from Product p inner join ProductVariant pv on pv.product.productId = p.productId " +
            "inner join fetch  Size s on s.sizeId = pv.size.sizeId " +
            "inner join fetch Color c on c.colorId = pv.color.colorId " +
            "inner join fetch  SubCategories sc on sc.subCategoryId = p.categories.subCategoryId " +
            "inner join fetch Categories  cg on cg.categorieId = sc.categories.categorieId " +
            "where (:colorId is null or c.colorId = :colorId) " +
            "and (:sizeId is null  or s.sizeId = :sizeId) " +
            "and (:categoryId is null or cg.categorieId = :categoryId) and " +
            "(:subCategoryId IS NULL OR sc.subCategoryId = :subCategoryId)")
    Integer getAllTotal(
            @Param("colorId") Integer colorId,
            @Param("sizeId") Integer sizeId,
            @Param("categoryId") Integer categoryId,
            @Param("subCategoryId") Integer subCategoryId);

    @Query("select ps from Product as ps \n" +
            "where ps.productId in (\n" +
            "select distinct p.productId from Product as p \n" +
            "inner join ProductVariant as pv on pv.product.productId = p.productId\n" +
            "inner join OrderDetails as od on od.productVariant.productVariantId = pv.productVariantId \n" +
            "inner join Order as o on o.orderId  = od.order.orderId\n" +
            "where o.status = 'COMPLETE'\n" +
            "group by pv.product.productId    \n" +
            "order by count(p.productId) desc \n" +
            ")")
    Page<Product> productBanChay(Pageable pageable);

    @Query("select p from Product p where  p.quality < 20 ")
    Page<Product> getProductOutStock(Pageable pageable);
}
