package com.yun.service;

import com.yun.pojo.SerialNumber;
import com.yun.repository.SerialNumberRepository;
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
public class SerialNumberService {

    @Autowired
    private SerialNumberRepository serialNumberRepository;

    public List<SerialNumber> findByProductId(Integer productId){
        return serialNumberRepository.findByProductId(productId);
    }

    public List<SerialNumber> findBySerialId(Integer productId){
        return serialNumberRepository.findBySerialId(productId);
    }

    public SerialNumber findBySerialNumber(String serialNumber){
        return serialNumberRepository.findBySerialNumber(serialNumber);
    }

    public SerialNumber add(SerialNumber serialNumber){
        SerialNumber save = serialNumberRepository.save(serialNumber);
        return save;
    }

    public void update(SerialNumber serialNumber){
        serialNumberRepository.save(serialNumber);
    }

    public void delete(Integer id){
        serialNumberRepository.deleteById(id);
    }

}

