package com.yun.service;


import com.github.pagehelper.Page;
import com.yun.mapper.ScoreMapper;
import com.yun.pojo.Product;
import com.yun.pojo.Score;
import com.yun.repository.ScoreRepository;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.DetailVO;
import com.yun.vo.ScoreVO;
import com.yun.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 报表
 */
@Service
public class ReportService {

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private ProductService productService;



    /**
     * 总账目
     *
     * @param params
     * @return
     */
    public JsonResult getTotal(Map<String, Object> params) {
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
    public DetailVO getDetail(Map<String, Object> params) {
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



}

