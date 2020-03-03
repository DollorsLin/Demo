package com.yun.service;


import com.github.pagehelper.Page;
import com.yun.mapper.PlaceMapper;
import com.yun.mapper.ProductLogMapper;
import com.yun.pojo.Place;
import com.yun.pojo.Product;
import com.yun.pojo.ProductLog;
import com.yun.repository.PlaceRepository;
import com.yun.repository.ProductLogRepository;
import com.yun.utils.PageUtils;
import com.yun.vo.PlaceVO;
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
public class ProductLogService {


    @Autowired
    private ProductLogRepository productLogRepository;

    @Autowired
    private ProductLogMapper productLogMapper;

    public ProductLog get(Integer id){
        return productLogRepository.findProductLogById(id);
    }

    public void add(ProductLog productLog) {
        productLogRepository.save(productLog);
    }

    public PageUtils search(Map<String, Object> params) {
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<ProductLog> result = startPage(page, limit);
        productLogMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
    }

}

