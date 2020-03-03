package com.yun.repository;


import com.yun.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Integer> , JpaSpecificationExecutor<Order>{

    Order findOrderById(Integer id);

    Order findFirstByProductIdOrderByIdDesc(Integer productId);

    Order findOrderByOtherOrderNo(String otherNo);
}