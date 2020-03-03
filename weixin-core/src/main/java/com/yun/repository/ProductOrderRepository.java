package com.yun.repository;


import com.yun.pojo.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> , JpaSpecificationExecutor<ProductOrder>{

    ProductOrder findProductOrderById(Integer id);

    ProductOrder findFirstByProductIdOrderByAddTimeDesc(Integer productId);

    ProductOrder findProductOrderByRelateId(Integer id);
}