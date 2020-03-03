package com.yun.service;


import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.Page;
import com.yun.mapper.ScoreMapper;
import com.yun.mapper.UserMapper;
import com.yun.pojo.Product;
import com.yun.pojo.Score;
import com.yun.repository.ScoreRepository;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.DataTruncation;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 报表
 */
@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductService productService;

    public void addScores(List<Score> scores) {
        scoreRepository.saveAll(scores);
    }

    public void add(Score score) {
        scoreRepository.save(score);
    }



    public PageUtils search(Map<String, Object> params) {
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<ScoreVO> result = startPage(page, limit);
        scoreMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
    }

    public Score findScore(Integer prodcutId, String serialNumber, Long position, Date createTime) {
        return scoreRepository.findScoreByProductIdAndSerialNumberAndPositionAndCreateTime(prodcutId, serialNumber, position, createTime);
    }

    /**
     * 总账目
     *
     * @param params
     * @return
     */
    public JsonResult statistics(Map<String, Object> params) {
        List<StatisticsVO> statistics = scoreMapper.statistics(params);
        Map<String, Object> map = new HashMap<>();
        map.put("num", productService.findMax(params));
        map.put("list", statistics);
        return JsonResult.ok(map);

    }


    /**
     * 账目明细
     *
     * @param params
     * @return
     */
    public DetailVO detail(Map<String, Object> params) {
        Integer productId = Integer.valueOf(params.get("productId").toString());
        DetailVO detailVO = new DetailVO();
        detailVO.setList(scoreMapper.detail(params));
        Product product = productService.get(productId);
        detailVO.setNum(product.getPositionNum());
        return detailVO;

    }

    /**
     *
     * @param params
     */
    public void deleteByTime(Map<String, Object> params) {
        scoreMapper.deleteByTime(params);
    }


    public Object getTotal(Map<String, Object> params) {
        return scoreMapper.getTotal(params);
    }

    public Object getTotalOne(Map<String, Object> params) {
        return scoreMapper.getTotalOne(params);
    }

    public List<Revenue> getTotalTwo(Map<String, Object> params) {
        return scoreMapper.getTotalTwo(params);
    }

    public List<Revenue> getOrder(Map<String, Object> params) {
        return scoreMapper.getOrder(params);
    }

    public Object getDetail(Map<String, Object> params) {
        return scoreMapper.getDetail(params);
    }

    public Object getDetailOne(Map<String, Object> params) {
        return scoreMapper.getDetailOne(params);
    }

    public List<RevenueDetail> getDetailTwo(Map<String, Object> params) {
        return scoreMapper.getDetailTwo(params);
    }

    public List<RevenueDetail> getOrderDetail(Map<String, Object> params) {
        return scoreMapper.getOrderDetail(params);
    }

    public Object songTotal(Map<String, Object> params) {
        return userMapper.songTotal(params);
    }

    public Object getSongDetail(Map<String, Object> params) {
        return userMapper.getSongDetail(params);
    }

    public Object getScanDetail(Map<String, Object> params) {
        return userMapper.getScanDetail(params);
    }

    public Object scanTotal(Map<String, Object> params) {
        return userMapper.scanTotal(params);
    }

}

