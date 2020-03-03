package com.yun.controller;


import com.yun.pojo.Place;
import com.yun.service.PlaceService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.PlaceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "场地", tags = {"场地"})
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;



    @RequestMapping(value = "/get", method = POST)
    @ApiOperation(value = "查询", notes = "查询")
    public Place get(@RequestParam(value = "id") Integer id) {
        return placeService.get(id);
    }

    @RequestMapping(value = "/findByCustomerId", method = POST)
    @ApiOperation(value = "查询", notes = "查询")
    public List<Place> findByCustomerId(@RequestParam Integer customerId) {
        return placeService.findByCustomerId(customerId);
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody Place place) {
        place.setAddTime(new Date());
        placeService.add(place);
    }

    @RequestMapping(value = "/update", method = POST)
    @ApiOperation(value = "更新", notes = "更新")
    public void update(@RequestBody Place place) {
        placeService.update(place);
    }

    @RequestMapping(value = "/list", method = POST)
    @ApiOperation(value = "查询所有")
    public JsonResult<List<Place>> list() {
        List<Place> list = placeService.list();
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<PlaceVO>> search(@RequestParam Map<String, Object> params) {
        PageUtils search = placeService.search(params);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }

    @RequestMapping(value = "/delete", method = POST)
    @ApiOperation(value = "删除", notes = "删除")
    public void delete(@RequestParam Integer id) {
        placeService.delete(id);
    }

}
