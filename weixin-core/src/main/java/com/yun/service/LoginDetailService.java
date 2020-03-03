package com.yun.service;

import com.yun.pojo.LoginDetail;
import com.yun.repository.LoginDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录明细表
 */
@Service
public class LoginDetailService {

    @Autowired
    private LoginDetailRepository loginDetailRepository;

    public LoginDetail save(LoginDetail loginDetail) {
        return loginDetailRepository.save(loginDetail);
    }

    public List<LoginDetail> list(Map<String, Object> params) {
        List<LoginDetail> list = loginDetailRepository.findAll();
        return list;
    }

    public Integer loginTimes(Integer userId) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String startTime = sf.format(date);
            Date start = null;
            start = sf.parse(startTime);
            long startSecond = start.getTime();
            startTime = sf2.format(start);
            Date s = sf2.parse(startTime);
            String endTime = sf2.format(startSecond + 60 * 60 * 24 * 1000l);
            Date e = sf2.parse(endTime);
            List<LoginDetail> allByLoginTimeBetween = loginDetailRepository.findAllByUserIdAndLoginTimeBetween(userId, s, e);
            return allByLoginTimeBetween.size();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public LoginDetail getById(Integer id) {
        return loginDetailRepository.findLoginDetailById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id : ids) {
            loginDetailRepository.deleteById(id);
        }
    }
}

