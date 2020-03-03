package com.yun.controller;

import com.yun.pojo.SerialNumber;
import com.yun.service.SerialNumberService;
import com.yun.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "加密狗", tags = {"加密狗"})
@RequestMapping("/serialNumber")
public class SerialNumberController {

    @Autowired
    private SerialNumberService serialNumberService;

    @RequestMapping(value = "/findByProductId", method = POST)
    @ApiOperation(value = "查询加密狗串号", notes = "查询加密狗串号")
    public List<SerialNumber> findByProductId(@RequestParam(value = "productId") Integer productId) {
        return serialNumberService.findByProductId(productId);
    }

    @RequestMapping(value = "/findBySerialId", method = POST)
    @ApiOperation(value = "查询加密狗串号", notes = "查询加密狗串号")
    public List<SerialNumber> findBySerialId(@RequestParam(value = "productId") Integer productId) {
        return serialNumberService.findBySerialId(productId);
    }


    @RequestMapping(value = "/findBySerialNumber", method = POST)
    @ApiOperation(value = "查询加密狗串号", notes = "查询加密狗串号")
    public SerialNumber findBySerialNumber(@RequestParam(value = "serialNumber") String serialNumber) {
        SerialNumber bySerialNumber = serialNumberService.findBySerialNumber(serialNumber);
        return bySerialNumber;
    }


    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    JsonResult<SerialNumber> add(@RequestBody SerialNumber serialNumber) {
        SerialNumber add = serialNumberService.add(serialNumber);
        JsonResult result = JsonResult.ok();
        result.setData(add);
        return result;
    }

    @RequestMapping(value = "/update", method = POST)
    @ApiOperation(value = "更新", notes = "更新")
    public void update(@RequestBody SerialNumber serialNumber) {
        serialNumberService.update(serialNumber);
    }

    @RequestMapping(value = "/delete", method = POST)
    @ApiOperation(value = "删除", notes = "删除")
    public void delete(@RequestParam(value = "id") Integer id) {
        serialNumberService.delete(id);
    }

}
