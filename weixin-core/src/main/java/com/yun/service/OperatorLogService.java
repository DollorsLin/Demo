package com.yun.service;

import com.yun.pojo.OperatorLog;
import com.yun.repository.OperatorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机台操作日志
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-03 19:35:03
 */
@Service
public class OperatorLogService {

    @Autowired
    private OperatorLogRepository operatorLogRepository;

    public OperatorLog save(OperatorLog operatorLog) {
        return operatorLogRepository.save(operatorLog);
    }


}

