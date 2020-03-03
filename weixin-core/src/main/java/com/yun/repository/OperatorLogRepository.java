package com.yun.repository;

import com.yun.pojo.OperatorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OperatorLogRepository extends JpaRepository<OperatorLog, Integer> , JpaSpecificationExecutor<OperatorLog>{

    OperatorLog findOperatorLogById(Integer id);
}