package com.yun.repository;

import com.yun.pojo.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogRepository extends JpaRepository<Log, Integer> , JpaSpecificationExecutor<Log>{

    Log findLogById(Integer id);
}