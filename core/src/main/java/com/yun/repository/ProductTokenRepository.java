package com.yun.repository;

import com.yun.pojo.ProductToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductTokenRepository extends JpaRepository<ProductToken, Integer> , JpaSpecificationExecutor<ProductToken>{

    ProductToken findProductTokenById(Integer id);
    ProductToken findProductTokenBySerialNumber(String serialNumber);
    ProductToken findProductTokenByToken(String token);
}