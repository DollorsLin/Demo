package com.yun.repository;


import com.yun.pojo.PlayDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PlayDetailRepository extends JpaRepository<PlayDetail, Integer> , JpaSpecificationExecutor<PlayDetail>{

    PlayDetail findPlayDetailById(Integer id);

    List<PlayDetail> findAllByUserId(Integer userId);
}