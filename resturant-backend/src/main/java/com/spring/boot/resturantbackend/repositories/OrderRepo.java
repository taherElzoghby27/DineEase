package com.spring.boot.resturantbackend.repositories;

import com.spring.boot.resturantbackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByAccountIdOrderByUpdatedDateDesc(Long accountId);

    @Query(value = "select o from Order o order by o.updatedDate desc ")
    List<Order> findAllOrderByUpdatedDateDesc();
}
