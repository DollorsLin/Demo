package com.yun.service;

import com.yun.mapper.UserMapper;
import com.yun.pojo.User;
import com.yun.repository.UserRepository;
import com.yun.vo.UserToRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户表
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-11-29 14:09:23
 */
@Service
public class  UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> list( Map<String, Object> params){
        List<User> list = userRepository.findAll();
        return list;
    }



    public User getById(Integer id) {
        return userRepository.findUserById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            userRepository.deleteById(id);
        }
    }

    public User getByOpenId(String openId) {
        return userRepository.findUserByOpenId(openId);
    }


    public List<UserToRoleVO> rankList(Map<String, Object> params) {
        return userMapper.rankList(params);
    }

    public List<UserToRoleVO> rank(Integer userId) {
        List<UserToRoleVO> rank = userMapper.rank(userId);
        return rank;
    }
}

