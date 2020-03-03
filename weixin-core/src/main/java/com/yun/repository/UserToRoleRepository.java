package com.yun.repository;

import com.yun.pojo.UserToRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserToRoleRepository extends JpaRepository<UserToRole, Integer> , JpaSpecificationExecutor<UserToRole>{

    UserToRole findUserToRoleById(Integer id);

    List<UserToRole> findAllByUserId(Integer userId);
}