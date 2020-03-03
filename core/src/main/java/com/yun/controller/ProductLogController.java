package com.yun.controller;

import com.yun.pojo.ProductLog;
import com.yun.service.ProductLogService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "机台日志", tags = {"机台日志"})
@RequestMapping("/productLog")
public class ProductLogController {

    @Autowired
    private ProductLogService productLogService;

    @RequestMapping(value = "/get", method = POST)
    @ApiOperation(value = "查询", notes = "查询")
    public ProductLog get(@RequestParam(value = "id") Integer id) {
        return productLogService.get(id);
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增列表",notes = "新增列表")
    public void add(@RequestBody ProductLog productLog){
        productLogService.add(productLog);
    }

    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<ProductLog>> search(@RequestParam Map<String, Object> params) {
        PageUtils search = productLogService.search(params);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }

}
