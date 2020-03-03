package com.yun.service;


import com.yun.pojo.PlayDetail;
import com.yun.repository.PlayDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 玩家游戏明细
 *
 */
@Service
public class  PlayDetailService {

    @Autowired
    private PlayDetailRepository playDetailRepository;

    public PlayDetail save(PlayDetail playDetail) {
        return playDetailRepository.save(playDetail);
    }

    public List<PlayDetail> list( Map<String, Object> params){
        List<PlayDetail> list = playDetailRepository.findAll();
        return list;
    }


    public PlayDetail getById(Integer id) {
        return playDetailRepository.findPlayDetailById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            playDetailRepository.deleteById(id);
        }
    }

    public List<PlayDetail> detailList(Integer userId){
        List<PlayDetail> list = playDetailRepository.findAllByUserId(userId);
        return list;
    }
}

