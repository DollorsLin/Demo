package com.yun.repository;

import com.yun.pojo.UpdatePackageToProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UpdatePackageToProductRepository extends JpaRepository<UpdatePackageToProduct, Integer> , JpaSpecificationExecutor<UpdatePackageToProduct>{

    UpdatePackageToProduct findTopBySerialNumberOrderByIdDesc(String serialNumber);

//    UpdatePackageToProduct findTopByProductIdOrderByAddTimeDesc(Integer productId);
}