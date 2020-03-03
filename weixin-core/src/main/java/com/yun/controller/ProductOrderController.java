package com.yun.controller;

import com.yun.pojo.ProductOrder;
import com.yun.pojo.ProductToUser;
import com.yun.service.ProductOrderService;
import com.yun.service.ProductToUserService;
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
 * 机台订单关联表
 *
 */
@RestController
@Api(value = "ProductOrderController")
@RequestMapping("/productOrder")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;


    @RequestMapping("/get")
    @ApiOperation(value = "获取productOrder",notes = "根据id获取productOrder")
    @ApiImplicitParam(paramType ="query",name = "id",required = true)
    public ProductOrder getById(@RequestParam(value = "id") Integer id){
        return productOrderService.getById(id);
    }


    @RequestMapping("/getByOrderId")
    @ApiOperation(value = "获取productOrder",notes = "根据id获取productOrder")
    @ApiImplicitParam(paramType ="query",name = "id",required = true)
    public ProductOrder getByOrderId(@RequestParam(value = "orderId") Integer orderId){
        return productOrderService.getByOrderId(orderId);
    }


    @RequestMapping("/update")
    @ApiOperation(value = "修改productOrder",notes = "根据id修改productOrder")
    @ApiImplicitParam(paramType ="query",name = "productOrder",required = true)
    public void updateById(@RequestBody ProductOrder productOrder){
            productOrderService.save(productOrder);
    }



}
