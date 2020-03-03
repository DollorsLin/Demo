package com.yun.service;

import com.yun.pojo.PlayerAccountDetail;
import com.yun.repository.PlayerAccountDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 玩家账户明细表
 *
 */
@Service
public class  PlayerAccountDetailService {

    @Autowired
    private PlayerAccountDetailRepository playerAccountDetailRepository;

    public PlayerAccountDetail save(PlayerAccountDetail playerAccountDetail) {
        return playerAccountDetailRepository.save(playerAccountDetail);
    }

    public List<PlayerAccountDetail> list( Map<String, Object> params){
        List<PlayerAccountDetail> list = playerAccountDetailRepository.findAll();
        return list;
    }


    public PlayerAccountDetail getById(Integer id) {
        return playerAccountDetailRepository.findPlayerAccountDetailById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            playerAccountDetailRepository.deleteById(id);
        }
    }
}

