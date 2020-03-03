package com.yun.repository;

import com.yun.pojo.ProductLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductLogRepository extends JpaRepository<ProductLog, Integer>, JpaSpecificationExecutor<ProductLog> {
    ProductLog findProductLogById(Integer id);
}