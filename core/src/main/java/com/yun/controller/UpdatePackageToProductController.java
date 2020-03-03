package com.yun.controller;


import com.yun.pojo.UpdatePackageToProduct;
import com.yun.service.UpdatePackageToProductService;
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
@Api(value = "升级包与机台关系", tags = {"升级包与机台关系"})
@RequestMapping("/updatePackageToProduct")

public class UpdatePackageToProductController {

    @Autowired
    private UpdatePackageToProductService updatePackageToProductService;


    @RequestMapping(value = "/findTopBySerialNumber", method = POST)
    @ApiOperation(value = "根据SerialNumber查询", notes = "根据SerialNumber查询")
    public JsonResult<UpdatePackageToProduct> findTopBySerialNumber(@RequestParam String serialNumber) {
        UpdatePackageToProduct topByProductIdOrderByProductIdDesc = updatePackageToProductService.findTopBySerialNumberOrderByAddTimeDesc(serialNumber);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(topByProductIdOrderByProductIdDesc);
        return jsonResult;
    }

//    @RequestMapping(value = "/findTopByProductId", method = POST)
//    @ApiOperation(value = "根据productId查询", notes = "根据productId查询")
//    public JsonResult<UpdatePackageToProduct> findTopByProductId(@RequestParam Integer productId) {
//        UpdatePackageToProduct topByProductIdOrderByProductIdDesc = updatePackageToProductService.findTopByProductIdOrderByAddTimeDesc(productId);
//        JsonResult jsonResult = new JsonResult();
//        jsonResult.setData(topByProductIdOrderByProductIdDesc);
//        return jsonResult;
//    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody UpdatePackageToProduct updatePackageToProduct) {
        updatePackageToProductService.add(updatePackageToProduct);
    }

    @RequestMapping(value = "/addList", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void addList(@RequestBody List<UpdatePackageToProduct> updatePackageToProducts) {
        updatePackageToProductService.addList(updatePackageToProducts);
    }

}
