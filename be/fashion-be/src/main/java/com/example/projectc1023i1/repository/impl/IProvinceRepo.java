package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProvinceRepo extends JpaRepository<Province, Long> {
    @Query("SELECT COUNT(p) > 0 FROM Province p WHERE p.code = :code")
    boolean existsProvinceByCode(@Param("code") String code);

    @Query("select  p from Province p where p.code = :code")
    Province findProvinceByCode(@Param("code") String code);

}
