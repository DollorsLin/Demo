package com.yun.service;


import com.github.pagehelper.Page;
import com.yun.mapper.GameCategoryMapper;
import com.yun.pojo.GameCategory;
import com.yun.repository.GameCategoryRepository;
import com.yun.utils.PageUtils;
import com.yun.vo.GameCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 场地表，客户可多个场地，场地可多个机台
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class GameCategoryService {

    @Autowired
    private GameCategoryRepository gameCategoryRepository;
    @Autowired
    private GameCategoryMapper gameCategoryMapper;

    public void add(GameCategory gameCategory) {
        gameCategoryRepository.save(gameCategory);
    }

    public void update(GameCategory gameCategory) {
        gameCategoryRepository.save(gameCategory);
    }


    public GameCategory get(Integer id){
        return gameCategoryRepository.findGameCategoryById(id);
    }

    public List<GameCategory> list(){
        List<GameCategory> list = gameCategoryRepository.findAll();
        return list;
    }

    public PageUtils search(Map<String, Object> params){
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<GameCategoryVO> result = startPage(page, limit);
        gameCategoryMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
    }

}

