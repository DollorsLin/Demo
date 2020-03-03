package com.yun.controller;

import java.util.List;
import java.util.Map;

import com.yun.pojo.PlayerAccountDetail;
import com.yun.service.PlayerAccountDetailService;
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
 * 玩家账户明细表
 *
 */
@RestController
@Api(value = "PlayerAccountDetailController")
@RequestMapping("/playerAccountDetail")
public class PlayerAccountDetailController{

    @Autowired
    private PlayerAccountDetailService playerAccountDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "查询所有")
    public JsonResult list(@RequestParam Map<String, Object> params){
        List<PlayerAccountDetail> list = playerAccountDetailService.list(params);
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }


    @RequestMapping("/getById")
    @ApiOperation(value = "获取playerAccountDetail",notes = "根据id获取playerAccountDetail")
    @ApiImplicitParam(paramType ="query",name = "id",required = true)
    public PlayerAccountDetail getById(@RequestParam(value = "id") Integer id){
        return playerAccountDetailService.getById(id);
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增playerAccountDetail",notes = "新增playerAccountDetail")
    @ApiImplicitParam(paramType ="query",name = "playerAccountDetail",required = true)
    public void save(@RequestBody PlayerAccountDetail playerAccountDetail){
            playerAccountDetailService.save(playerAccountDetail);
    }

    @RequestMapping("/updateById")
    @ApiOperation(value = "修改playerAccountDetail",notes = "根据id修改playerAccountDetail")
    @ApiImplicitParam(paramType ="query",name = "playerAccountDetail",required = true)
    public void updateById(@RequestBody PlayerAccountDetail playerAccountDetail){
            playerAccountDetailService.save(playerAccountDetail);
    }




}
