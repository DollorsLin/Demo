package com.yun.repository;


import com.yun.pojo.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameCategoryRepository extends JpaRepository<GameCategory, Integer> , JpaSpecificationExecutor<GameCategory> {

    GameCategory findGameCategoryById(Integer id);


}