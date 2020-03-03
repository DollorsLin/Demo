package com.yun.controller;

import java.util.List;
import java.util.Map;

import com.yun.pojo.Order;
import com.yun.service.OrderService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




/**
 * 订单
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-12-12 13:59:20
 */
@RestController
@Api(value = "OrderController")
@RequestMapping("/order")
public class OrderController{

    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "查询所有")
    public JsonResult list(@RequestParam Map<String, Object> params){
        List<Order> list = orderService.list(params);
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }


    @RequestMapping("/getById")
    @ApiOperation(value = "获取order",notes = "根据id获取order")
    @ApiImplicitParam(paramType ="query",name = "id",required = true)
    public Order getById(@RequestParam(value = "id") Integer id){
        return orderService.getById(id);
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增order",notes = "新增order")
    @ApiImplicitParam(paramType ="query",name = "order",required = true)
    public void save(@RequestBody Order order){
            orderService.save(order);
    }

    @RequestMapping("/updateById")
    @ApiOperation(value = "修改order",notes = "根据id修改order")
    @ApiImplicitParam(paramType ="query",name = "order",required = true)
    public void updateById(@RequestBody Order order){
            orderService.save(order);
    }




}
