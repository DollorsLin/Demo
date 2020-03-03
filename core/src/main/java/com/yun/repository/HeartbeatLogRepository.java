package com.yun.repository;

import com.yun.pojo.HeartbeatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HeartbeatLogRepository extends JpaRepository<HeartbeatLog, Integer> , JpaSpecificationExecutor<HeartbeatLog>{

    HeartbeatLog findHeartbeatLogById(Integer id);

    HeartbeatLog findFirstByProductIdOrderByAddTime(Integer productId);
}