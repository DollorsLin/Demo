package com.yun.repository;

import com.yun.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Integer> , JpaSpecificationExecutor<Product>{

    Product findProductById(Integer id);
}