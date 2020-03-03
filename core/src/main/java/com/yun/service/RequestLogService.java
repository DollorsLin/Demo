package com.yun.service;

import com.yun.pojo.RequestLog;
import com.yun.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户端操作日志
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-03 21:29:32
 */
@Service
public class  RequestLogService {

    @Autowired
    private RequestLogRepository requestLogRepository;

    public RequestLog add(RequestLog requestLog) {
        return requestLogRepository.save(requestLog);
    }

}

