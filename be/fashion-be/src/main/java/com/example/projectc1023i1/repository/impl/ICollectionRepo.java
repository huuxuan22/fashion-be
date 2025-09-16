package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICollectionRepo extends JpaRepository<Collection, Long> {
    @Query("select c from Collection c order by c.startDate desc limit 1")
     Collection findByStartDate();

    @Query("select c from Collection c order by c.startDate asc limit 1 offset 1")
    Collection findByLast();

    @Query("select  c from Collection c where  c.name like concat('%',:search,'%') ")
    Page<Collection> findByIsActive(Pageable pageable, @Param("search") String search);

    @Query("select  count(c) from Collection c where  c.name like concat('%',:search,'%') ")
    Integer countAll(@Param("search") String search );
}
