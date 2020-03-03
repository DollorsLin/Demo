package com.yun.repository;

import com.yun.pojo.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

public interface ScoreRepository extends JpaRepository<Score, Integer> , JpaSpecificationExecutor<Score>{

    Score findScoreById(Integer id);


    Score findScoreByProductIdAndSerialNumberAndPositionAndCreateTime(Integer productId, String serialNumber, Long position, Date createTime);

}