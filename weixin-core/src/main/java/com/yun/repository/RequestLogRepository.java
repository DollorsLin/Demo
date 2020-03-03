package com.yun.repository;

import com.yun.pojo.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestLogRepository extends JpaRepository<RequestLog, Integer> , JpaSpecificationExecutor<RequestLog>{

    RequestLog findRequestLogById(Integer id);
}