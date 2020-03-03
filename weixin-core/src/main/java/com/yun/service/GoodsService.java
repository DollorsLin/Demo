package com.yun.service;


import com.yun.pojo.Goods;
import com.yun.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品
 *
 */
@Service
public class  GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    public Goods save(Goods goods) {
        return goodsRepository.save(goods);
    }

    public List<Goods> list( Map<String, Object> params){
        List<Goods> list = goodsRepository.findAll();
        return list;
    }


    public Goods getById(Integer id) {
        return goodsRepository.findGoodsById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            goodsRepository.deleteById(id);
        }
    }
}

