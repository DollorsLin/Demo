package com.yun.repository;

import com.yun.pojo.Jp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpRepository extends JpaRepository<Jp, Integer> , JpaSpecificationExecutor<Jp>{


}