package com.yun.repository;

import com.yun.pojo.UpdatePackageConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UpdatePackageConfigRepository extends JpaRepository<UpdatePackageConfig, Integer> , JpaSpecificationExecutor<UpdatePackageConfig>{
    UpdatePackageConfig findByUpdatePackageId(Integer updatePackageId);
}