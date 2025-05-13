package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.AddressUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IAddressUserRepo extends JpaRepository<AddressUser, Integer> {
    @Query("select a from  AddressUser a where a.user.userId = :param order by a.addressUserId desc ")
    List<AddressUser> findByUser(@Param("param") Integer param);

    @Transactional
    @Modifying
    @Query("delete from AddressUser where addressUserId = :id")
    void deleteById(@Param("id") Integer id);
}
