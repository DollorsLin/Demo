package com.yun.service;

import com.yun.pojo.Role;
import com.yun.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色表
 *
 */
@Service
public class  RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> list(){
        List<Role> list = roleRepository.findAll();
        return list;
    }


    public Role getById(Integer id) {
        return roleRepository.findRoleById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            roleRepository.deleteById(id);
        }
    }
}

