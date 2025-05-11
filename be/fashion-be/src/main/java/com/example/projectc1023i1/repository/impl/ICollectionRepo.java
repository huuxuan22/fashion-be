package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICollectionRepo extends JpaRepository<Collection, Long> {
    @Query("select c from Collection c order by c.startDate desc limit 1")
     Collection findByStartDate();

    @Query("select c from Collection c order by c.startDate asc limit 1 offset 1")
    Collection findByLast();
}
