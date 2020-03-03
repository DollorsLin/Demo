package com.yun.repository;

import com.yun.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> , JpaSpecificationExecutor<Product>{

    Product findProductById(Integer id);

    List<Product> findAllByPlaceId(Integer placeId);

    List<Product> findAllByIsOnline(Integer isOnline);
}