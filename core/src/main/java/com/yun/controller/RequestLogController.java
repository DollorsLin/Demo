package com.yun.controller;

import com.yun.pojo.RequestLog;
import com.yun.service.RequestLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "客户端操作日志", tags = {"客户端操作日志"})
@RequestMapping("/requestLog")
public class RequestLogController{

    @Autowired
    private RequestLogService requestLogService;


    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增",notes = "新增")
    public void add(@RequestBody RequestLog requestLog){
        requestLog.setAddTime(new Date());
        requestLogService.add(requestLog);
    }


}
