package com.yun.controller;

import java.util.Map;
import java.util.List;

import com.yun.pojo.Goods;
import com.yun.service.GoodsService;
import com.yun.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 商品
 *
 */
@RestController
@Api(value = "GoodsController")
@RequestMapping("/goods")
public class GoodsController{

    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "查询所有")
    public JsonResult list(@RequestParam Map<String, Object> params){
        List<Goods> list = goodsService.list(params);
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }


    @RequestMapping("/getById")
    @ApiOperation(value = "获取goods",notes = "根据id获取goods")
    @ApiImplicitParam(paramType ="query",name = "id",required = true)
    public Goods getById(@RequestParam(value = "id") Integer id){
        return goodsService.getById(id);
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增goods",notes = "新增goods")
    @ApiImplicitParam(paramType ="query",name = "goods",required = true)
    public void save(@RequestBody Goods goods){
            goodsService.save(goods);
    }

    @RequestMapping("/updateById")
    @ApiOperation(value = "修改goods",notes = "根据id修改goods")
    @ApiImplicitParam(paramType ="query",name = "goods",required = true)
    public void updateById(@RequestBody Goods goods){
            goodsService.save(goods);
    }




}
