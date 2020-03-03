package com.yun.repository;


import com.yun.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, Integer> , JpaSpecificationExecutor<Role>{

    Role findRoleById(Integer id);
}