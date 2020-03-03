package com.yun.controller;

import com.yun.pojo.HeartbeatLog;
import com.yun.service.HeartbeatLogService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "机台心跳日志", tags = {"机台心跳日志"})
@RequestMapping("/heartbeatLog")
public class HeartbeatLogController{

    @Autowired
    private HeartbeatLogService heartbeatLogService;

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增",notes = "新增")
    public void add(@RequestBody HeartbeatLog heartbeatLog){
        heartbeatLog.setAddTime(new Date());
        heartbeatLogService.add(heartbeatLog);
    }

    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<HeartbeatLog>> search(@RequestParam Map<String, Object> params) {
        PageUtils search = heartbeatLogService.search(params);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }
}
