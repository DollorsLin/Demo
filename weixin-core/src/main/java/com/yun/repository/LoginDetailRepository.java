package com.yun.repository;


import com.yun.pojo.LoginDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface LoginDetailRepository extends JpaRepository<LoginDetail, Integer> , JpaSpecificationExecutor<LoginDetail>{

    LoginDetail findLoginDetailById(Integer id);

    List<LoginDetail> findAllByUserIdAndLoginTimeBetween(Integer userId, Date startTime, Date endTIme);
}