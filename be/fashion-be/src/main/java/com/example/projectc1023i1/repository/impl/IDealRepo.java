package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDealRepo extends JpaRepository<Deal, Integer> {
    @Query("update Deal set dealStatus = :dealStatus where dealId = :dealId")
    @Transactional
    @Modifying
    void updateDealStatus(@Param("dealId") Integer dealId,@Param("dealStatus") String dealStatus);

    @Query("select count(d) from Deal d")
    Integer getAllPage();
    @Transactional
    @Modifying
    @Query("delete Deal where dealId = :dealId")
    void deleteDeal(@Param("dealId") Integer dealId);

    @Query("select d from Deal d where d.product.productId = :productId and d.dealStatus = 'CREATE' ")
    List<Deal> findByProduct(@Param("productId") Integer productId);
}
