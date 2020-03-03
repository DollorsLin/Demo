package com.yun.controller;

import com.yun.pojo.Serial;
import com.yun.pojo.SerialNumber;
import com.yun.service.SerialNumberService;
import com.yun.service.SerialService;
import com.yun.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "加密狗系列", tags = {"加密狗系列"})
@RequestMapping("/serial")
public class SerialController {

    @Autowired
    private SerialService serialService;

    @RequestMapping(value = "/list", method = POST)
    @ApiOperation(value = "查询所有", notes = "查询所有")
    public JsonResult<List<Serial>> list() {
        List<Serial> list = serialService.list();
        JsonResult ok = JsonResult.ok();
        ok.setData(list);
        return ok;
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    JsonResult<Serial> add(@RequestBody Serial serial){
        serial.setAddTime(new Date());
        Serial add = serialService.add(serial);
        JsonResult result = JsonResult.ok();
        result.setData(add);
        return result;
    }

}
