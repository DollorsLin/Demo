package com.yun.repository;

import com.yun.pojo.Command;
import com.yun.pojo.Log;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Integer>, JpaSpecificationExecutor<Command> {

    Command findCommandById(Integer id);

    List<Command> findCommandBySerialNumberAndIsDeleted(String productId, Integer isDeleted);

    Command findFirstByProductIdAndCommandOrderByAddTimeDesc(Integer productId, Integer command);

}