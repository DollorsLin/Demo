package com.yun.controller;

import com.yun.pojo.User;
import com.yun.service.LoginDetailService;
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
 */
@RestController
@Api(value = "UserController")
@RequestMapping("/testtt")
public class LoginController {

    @Autowired
    private LoginDetailService loginDetailService;




}
