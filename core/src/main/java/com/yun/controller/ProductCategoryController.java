package com.yun.controller;

import com.yun.pojo.ProductCategory;
import com.yun.service.ProductCategoryService;
import com.yun.service.SerialService;
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
@Api(value = "产品分类", tags = {"产品分类"})
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    SerialService serialService;

    @RequestMapping(value = "/get", method = POST)
    @ApiOperation(value = "查询", notes = "查询")
    public ProductCategory get(@RequestParam(value = "id") Integer id) {
        return productCategoryService.get(id);
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody ProductCategory productCategory) {
        productCategoryService.add(productCategory);
    }


    @RequestMapping(value = "/update", method = POST)
    @ApiOperation(value = "更新", notes = "更新")
    public void update(@RequestBody ProductCategory productCategory) {
        productCategoryService.update(productCategory);
    }


    @RequestMapping(value = "/list", method = POST)
    @ApiOperation(value = "查询所有")
    public JsonResult<List<ProductCategory>> list() {
        List<ProductCategory> list = productCategoryService.list();
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }


    @RequestMapping(value = "/findBySerialId", method = POST)
    @ApiOperation(value = "根据加密狗系列查询")
    public JsonResult<List<ProductCategory>> findBySerialId(@RequestParam(value = "serialId") Integer serialId) {
        List<ProductCategory> productCategoryBySerialId = productCategoryService.findProductCategoryBySerialId(serialId);
        JsonResult result = JsonResult.ok();
        result.setData(productCategoryBySerialId);
        return result;
    }

}
