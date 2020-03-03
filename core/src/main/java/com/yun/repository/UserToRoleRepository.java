package com.yun.repository;

import com.yun.pojo.UserToRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserToRoleRepository extends JpaRepository<UserToRole, Integer> , JpaSpecificationExecutor<UserToRole>{

    UserToRole findUserToRoleById(Integer id);

    UserToRole findUserToRoleByUserIdAndRoleId(Integer userId, Integer roleId);
}