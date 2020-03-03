package com.yun.repository;

import com.yun.pojo.ProductSerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductSerialNumberRepository extends JpaRepository<ProductSerialNumber, Integer>, JpaSpecificationExecutor<ProductSerialNumber> {

    ProductSerialNumber findProductSerialNumberById(Integer id);

    List<ProductSerialNumber> findByProductIdAndIsDeleted(Integer productId, Integer isDeleted);

    ProductSerialNumber findBySerialNumber(String serialNumber);

}