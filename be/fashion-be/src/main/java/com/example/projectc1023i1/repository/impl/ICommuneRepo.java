package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICommuneRepo extends JpaRepository<Commune, Long> {
    @Query("select count(c) > 0 from Commune c where c.code = :code")
    boolean existsCommuneByCode(@Param("code") String code);

    @Query("select c from Commune c where c.code = :code")
    Commune findCommuneByCode(@Param("code") String code);
}
