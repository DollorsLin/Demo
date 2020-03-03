package com.yun.service;

import com.github.pagehelper.Page;
import com.yun.mapper.HeartbeatLogMapper;
import com.yun.pojo.HeartbeatLog;
import com.yun.repository.HeartbeatLogRepository;
import com.yun.utils.PageUtils;
import com.yun.vo.PlaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 心跳日志
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-03 19:35:03
 */
@Service
public class  HeartbeatLogService {
    @Autowired
    private HeartbeatLogMapper heartbeatLogMapper;

    @Autowired
    private HeartbeatLogRepository heartbeatLogRepository;

    public HeartbeatLog add(HeartbeatLog heartbeatLog) {
        return heartbeatLogRepository.save(heartbeatLog);
    }

    public PageUtils search(Map<String, Object> params){
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<HeartbeatLog> result = startPage(page, limit);
        heartbeatLogMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
    }

    public HeartbeatLog findLastByProductId(Integer productId) {
        return heartbeatLogRepository.findFirstByProductIdOrderByAddTime(productId);
    }

}

