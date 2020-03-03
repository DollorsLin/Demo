package com.yun.service;


import com.github.pagehelper.Page;
import com.yun.mapper.PlaceMapper;
import com.yun.pojo.Place;
import com.yun.repository.PlaceRepository;
import com.yun.utils.JsonResult;
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
public class  PlaceService {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceMapper placeMapper;

    public void add(Place place) {
        placeRepository.save(place);
    }

    public void update(Place place) {
        placeRepository.save(place);
    }


    public Place get(Integer id){
        return placeRepository.findPlaceById(id);
    }

    public List<Place> list(){
        List<Place> list = placeRepository.findAll();
        return list;
    }

    public PageUtils search(Map<String, Object> params){
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<PlaceVO> result = startPage(page, limit);
        placeMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
    }

    public List<Place> findByCustomerId(Integer customerId) {
        return placeRepository.findPlacesByCustomerId(customerId);
    }

    public void delete(Integer id) {
        placeRepository.deleteById(id);
    }


    public Object getPlaceList(Integer categoryId, Integer customerId) {
        return placeMapper.getPlaceList(categoryId, customerId);
    }
}

