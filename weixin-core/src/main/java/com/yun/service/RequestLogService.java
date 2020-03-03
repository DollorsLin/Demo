package com.yun.service;

import com.yun.pojo.RequestLog;
import com.yun.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户端操作日志
 *
 */
@Service
public class RequestLogService {

    @Autowired
    private RequestLogRepository requestLogRepository;

    public RequestLog add(RequestLog requestLog) {
        return requestLogRepository.save(requestLog);
    }

}

