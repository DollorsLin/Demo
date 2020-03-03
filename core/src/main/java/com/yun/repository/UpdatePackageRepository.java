package com.yun.repository;

import com.yun.pojo.UpdatePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UpdatePackageRepository extends JpaRepository<UpdatePackage, Integer> , JpaSpecificationExecutor<UpdatePackage>{

    UpdatePackage findUpdatePackageById(Integer id);

    List<UpdatePackage> findByCategoryId(Integer categoryId);

    List<UpdatePackage> findByProductCategoryId(Integer productCategoryId);

}