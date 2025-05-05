package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDistrictRepo extends JpaRepository<District, Integer> {
    @Query("select count(d) > 0 from District d where d.code = :code")
    boolean existsDistrictByCode(@Param("code") String code);

    @Query("select d from District  d where d.code = :code")
    District findDistrictByCode(@Param("code") String code);
}
