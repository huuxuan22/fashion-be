package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Notifcation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface INotificationRepo extends JpaRepository<Notifcation, Integer> {
    @Query("select n from Notifcation n where (n.user.userId = :userId or n.user.userId is null) ")
    Page<Notifcation> findAllNotification(@Param("userId") Integer userId, Pageable pageable);

    @Query("update Notifcation  set status = true ")
    @Transactional
    @Modifying
    void updateNotification();

    @Query("select n from Notifcation n where (n.user.userId = :userId or n.user.userId is null ) and (n.status = false )")
    List<Notifcation> findAllNotificationNew(@Param("userId") Integer userId);
}
