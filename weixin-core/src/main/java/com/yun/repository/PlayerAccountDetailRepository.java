package com.yun.repository;


import com.yun.pojo.PlayerAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlayerAccountDetailRepository extends JpaRepository<PlayerAccountDetail, Integer> , JpaSpecificationExecutor<PlayerAccountDetail>{

    PlayerAccountDetail findPlayerAccountDetailById(Integer id);
}