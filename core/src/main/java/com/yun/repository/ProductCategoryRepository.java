package com.yun.repository;

import com.yun.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> , JpaSpecificationExecutor<ProductCategory>{

    ProductCategory findProductCategoryById(Integer id);

    List<ProductCategory> findProductCategoryBySerialId(Integer id);
}