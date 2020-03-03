package com.yun.service;

import com.yun.pojo.Serial;
import com.yun.pojo.SerialNumber;
import com.yun.repository.SerialNumberRepository;
import com.yun.repository.SerialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机台加密狗
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class SerialService {

    @Autowired
    private SerialRepository serialRepository;

    public List<Serial> list() {
        return serialRepository.findAll();
    }

    public Serial add(Serial serial) {
        Serial save = serialRepository.save(serial);
        return save;
    }
}

