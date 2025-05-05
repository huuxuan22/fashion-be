package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.Dto.get_data.SizeAndQuality;
import com.example.projectc1023i1.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISizeRepo extends JpaRepository<Size, Integer> {
    @Query(value = "select distinct s.size_id,s.name_size,pv.stock from size as s  \n" +
            "inner join product_variants as pv on pv.size_id = s.size_id \n" +
            "where pv.product_id = :productId", nativeQuery = true)
    List<Object[]> getAllSizeOfOneProduct(@Param("productId") int productId);
}
