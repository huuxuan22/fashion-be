package com.example.projectc1023i1.repository.impl;

import com.example.projectc1023i1.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IOrderRepo extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.orderCode = :code")
     Order findByOrderCode(@Param("code") String code);

    @Query("select o from Order o where o.status like 'CREATE' OR o.status like 'DELIVERY' ")
    Page<Order> findAllOrderAdmin(Pageable pageable);

    @Query("select o from Order o where o.users.userId = :userId")
    Page<Order> findAllOrderUser(Pageable pageable,@Param("userId") Integer userId);
    @Query("select o from Order o where o.status like 'CREATE' ")
    Page<Order> findAllOrderAdminByCREATE(Pageable pageable);

    @Query("select o from Order o where o.status like 'DELIVERY' ")
    Page<Order> findAllOrderAdminByDELIVERy(Pageable pageable);

    @Query("select count(o) from Order o where o.status like :param ")
    Integer countOrder(@Param("param") String param);

    @Query("select count(o) from Order o ")
    Integer countOrderALL();

    @Query("select count(o) from Order o where o.users.userId = :userId")
    Integer countOrderALLUser(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("update Order  set status = 'CANCEL' where users.userId = :userId and orderId = :orderId")
    void cancelOrder(@Param("userId") Integer userId, @Param("orderId") Long orderId);

    // Kiểm tra đơn hàng đã tồn tại (đã được đặt)
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END " +
            "FROM Order o " +
            "WHERE o.orderId = :orderId AND o.users.userId = :userId and o.status = 'DELIVERY'")
    boolean existsByOrderIdAndUser(@Param("orderId") Long orderId, @Param("userId") Integer userId);

    // Kiểm tra trạng thái hủy của đơn hàng
    @Query("SELECT o.status = 'CANCEL' " +
            "FROM Order o " +
            "WHERE o.orderId = :orderId AND o.users.userId = :userId")
    Optional<Boolean> isOrderCancelled(@Param("orderId") Long orderId, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("update Order set status = 'DELIVERY' where orderId = :orderId")
    void deliveryOrder(@Param("orderId") Long orderId);

    @Modifying
    @Transactional
    @Query("update Order set status = 'COMPLETE' where orderId = :orderId")
    void completeOrder(@Param("orderId") Long orderId);
}
