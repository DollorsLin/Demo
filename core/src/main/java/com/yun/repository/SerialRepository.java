package com.yun.repository;

import com.yun.pojo.Serial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SerialRepository extends JpaRepository<Serial, Integer>, JpaSpecificationExecutor<Serial> {

}