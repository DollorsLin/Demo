package com.yun.service;


import com.github.pagehelper.Page;
import com.yun.mapper.JpMapper;
import com.yun.mapper.ScoreMapper;
import com.yun.pojo.Jp;
import com.yun.pojo.Score;
import com.yun.repository.JpRepository;
import com.yun.utils.PageUtils;
import com.yun.vo.JpVO;
import com.yun.vo.PlaceVO;
import com.yun.vo.ScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 机台分数
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class JPService {

    @Autowired
    private JpRepository jpRepository;
    @Autowired
    private JpMapper jpMapper;

    public void saveAll(List<Jp> jps) {
        jpRepository.saveAll(jps);
    }

    public void add(Jp jp) {
        jpRepository.save(jp);
    }

    public PageUtils search(Map<String, Object> params) {
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<JpVO> result = startPage(page, limit);
        jpMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
    }

    /**
     *
     * @param params
     */
    public void deleteByTime(Map<String, Object> params) {
        jpMapper.deleteByTime(params);
    }

}

