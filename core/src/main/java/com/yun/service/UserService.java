package com.yun.service;

import com.github.pagehelper.Page;
import com.yun.mapper.UserMapper;
import com.yun.pojo.PlayDetail;
import com.yun.pojo.PlayerAccountDetail;
import com.yun.pojo.User;
import com.yun.repository.UserRepository;
import com.yun.utils.PageUtils;
import com.yun.vo.UserToRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 用户表
 *
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayDetailService playDetailService;

    @Autowired
    private UserMapper userMapper;

    public User save(User user) {
        return userRepository.save(user);
    }

    public PageUtils list(Map<String, Object> params){
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<User> result = startPage(page, limit);
        userMapper.list(params);
        return new PageUtils(result);
    }



    public User getByOpenId(String openId) {

        return userRepository.findUserByOpenId(openId);
    }


    /**
     *
     * @param params type,1-经验值列表,2-账户明细,3-登录明细,4-游戏明细
     * @return
     */
    public PageUtils detail(Map<String, Object> params) {
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Integer type = Integer.parseInt(params.get("type").toString());
        Integer userId = Integer.parseInt(params.get("userId").toString());
        switch (type){
            case 1 :
                Page<UserToRoleVO> result = startPage(page, limit);
                userMapper.expList(userId);
                return new PageUtils(result);
            case 2 :
                Page<PlayerAccountDetail> result1 = startPage(page, limit);
                userMapper.accountList(userId);
                return new PageUtils(result1);
            case 3 :
                Page<User> result2 = startPage(page, limit);
                userMapper.loginList(userId);
                return new PageUtils(result2);
            case 4 :
                Page<PlayDetail> result3 = startPage(page, limit);
                userMapper.detailList(userId);
                return new PageUtils(result3);
        }
        return null;
    }

    public User getById(Integer userId) {
        return userRepository.findUserById(userId);
    }

    public List<UserToRoleVO> rank(Map<String, Object> params) {
        return userMapper.rank(params);
    }
}

