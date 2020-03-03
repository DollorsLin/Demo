package com.yun.repository;


import com.yun.pojo.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsRepository extends JpaRepository<Goods, Integer> , JpaSpecificationExecutor<Goods>{

    Goods findGoodsById(Integer id);
}