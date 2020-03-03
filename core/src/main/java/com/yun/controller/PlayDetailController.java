package com.yun.controller;


import java.util.List;
import java.util.Map;

import com.yun.pojo.PlayDetail;
import com.yun.service.PlayDetailService;
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
 * 玩家游戏明细
 *
 */
@RestController
@Api(value = "PlayDetailController")
@RequestMapping("/playDetail")
public class PlayDetailController{

    @Autowired
    private PlayDetailService playDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "查询所有")
    public JsonResult list(@RequestParam Map<String, Object> params){
        List<PlayDetail> list = playDetailService.list(params);
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }


    @RequestMapping("/getById")
    @ApiOperation(value = "获取playDetail",notes = "根据id获取playDetail")
    @ApiImplicitParam(paramType ="query",name = "id",required = true)
    public PlayDetail getById(@RequestParam(value = "id") Integer id){
        return playDetailService.getById(id);
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增playDetail",notes = "新增playDetail")
    @ApiImplicitParam(paramType ="query",name = "playDetail",required = true)
    public void save(@RequestBody PlayDetail playDetail){
            playDetailService.save(playDetail);
    }

//    @RequestMapping("/updateById")
//    @ApiOperation(value = "修改playDetail",notes = "根据id修改playDetail")
//    @ApiImplicitParam(paramType ="query",name = "playDetail",required = true)
//    public void updateById(@RequestBody PlayDetail playDetail){
//            playDetailService.save(playDetail);
//    }




}
