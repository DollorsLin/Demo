package com.yun.repository;


import com.yun.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Integer> , JpaSpecificationExecutor<User>{

    User findUserById(Integer id);

    User findUserByOpenId(String openId);
}