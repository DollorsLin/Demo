package com.yun.controller;

import com.yun.pojo.User;
import com.yun.service.UserService;
import com.yun.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 用户表
 *
 */
@RestController
@Api(value = "UserController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    @RequestMapping("/list")
    @ApiOperation(value = "查询所有")
    public JsonResult list(@RequestBody Map<String, Object> params){
        return JsonResult.ok(userService.list(params));
    }


    @RequestMapping("/detail")
    @ApiOperation(value = "获取明细",notes = "获取明细")
    public JsonResult detail(@RequestBody Map<String, Object> params){
        return JsonResult.ok(userService.detail(params));
    }


    @RequestMapping("/getByUserId")
    @ApiOperation(value = "查询所有")
    public User getByUserId(@RequestParam Integer userId){
        return userService.getById(userId);
    }

    @RequestMapping("/rank")
    @ApiOperation(value = "修改user",notes = "根据id修改user")
    @ApiImplicitParam(paramType ="query",name = "user",required = true)
    public JsonResult rank(@RequestBody Map<String, Object> params){
        return JsonResult.ok(userService.rank(params));
    }


}
