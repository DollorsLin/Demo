package com.yun.service;

import com.yun.pojo.UserToRole;
import com.yun.repository.UserToRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户角色关联表
 *
 */
@Service
public class  UserToRoleService {

    @Autowired
    private UserToRoleRepository userToRoleRepository;

    public UserToRole save(UserToRole userToRole) {
        return userToRoleRepository.save(userToRole);
    }

    public List<UserToRole> list( Map<String, Object> params){
        List<UserToRole> list = userToRoleRepository.findAll();
        return list;
    }

    public List<UserToRole> getListByUserId(Integer userId) {
        return userToRoleRepository.findAllByUserId(userId);
    }

    public UserToRole getById(Integer id) {
        return userToRoleRepository.findUserToRoleById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            userToRoleRepository.deleteById(id);
        }
    }
}

