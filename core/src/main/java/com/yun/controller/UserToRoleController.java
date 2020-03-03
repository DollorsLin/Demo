package com.yun.controller;


import java.util.List;
import java.util.Map;

import com.yun.pojo.UserToRole;
import com.yun.service.UserToRoleService;
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
 * 用户角色关联表
 *
 */
@RestController
@Api(value = "UserToRoleController")
@RequestMapping("/userToRole")
public class UserToRoleController{

    @Autowired
    private UserToRoleService userToRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "查询所有")
    public JsonResult list(@RequestParam Map<String, Object> params){
        List<UserToRole> list = userToRoleService.list(params);
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }


    @RequestMapping("/getByUserIdAndRoleId")
    @ApiOperation(value = "获取userToRole",notes = "根据id获取userToRole")
    @ApiImplicitParam(paramType ="query",name = "id",required = true)
    public UserToRole getByUserIdAndRoleId(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "roleId") Integer roleId){
        return userToRoleService.getByUserIdAndRoleId(userId,roleId);
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增userToRole",notes = "新增userToRole")
    @ApiImplicitParam(paramType ="query",name = "userToRole",required = true)
    public void save(@RequestBody UserToRole userToRole){
            userToRoleService.save(userToRole);
    }

    @RequestMapping("/updateById")
    @ApiOperation(value = "修改userToRole",notes = "根据id修改userToRole")
    @ApiImplicitParam(paramType ="query",name = "userToRole",required = true)
    public void updateById(@RequestBody UserToRole userToRole){
            userToRoleService.save(userToRole);
    }




}
