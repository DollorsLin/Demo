package com.yun.controller;

import java.util.Map;
import java.util.List;

import com.yun.pojo.ProductToUser;
import com.yun.service.ProductToUserService;
import com.yun.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 机台用户关联表
 *
 */
@RestController
@Api(value = "ProductToUserController")
@RequestMapping("/productToUser")
public class ProductToUserController{

    @Autowired
    private ProductToUserService productToUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "查询所有")
    public JsonResult<ProductToUser> list(@RequestParam Map<String, Object> params){
        List<ProductToUser> list = productToUserService.list(params);
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }



    @RequestMapping("/getById")
    @ApiOperation(value = "获取productToUser",notes = "根据id获取productToUser")
    @ApiImplicitParam(paramType ="query",name = "id",required = true)
    public ProductToUser getById(@RequestParam(value = "id") Integer id){
        return productToUserService.getById(id);
    }


    @RequestMapping("/getByOpenId")
    @ApiOperation(value = "获取productToUser",notes = "根据openId获取productToUser")
    public ProductToUser getByOpenId(@RequestParam(value = "openId") String openId){
        return productToUserService.getByOpenId(openId);
    }


    @RequestMapping("/getByProductIdAndPosition")
    @ApiOperation(value = "获取productToUser",notes = "根据id获取productToUser")
    public ProductToUser getByProductIdAndPosition(@RequestParam(value = "productId") Integer productId,
                                                   @RequestParam(value = "position") Integer position){
        return productToUserService.getByProductIdAndPosition(productId,position);
    }

    @RequestMapping("/save")
    @ApiOperation(value = "新增productToUser",notes = "新增productToUser")
    @ApiImplicitParam(paramType ="query",name = "productToUser",required = true)
    public void save(@RequestBody ProductToUser productToUser){
            productToUserService.save(productToUser);
    }

    @RequestMapping("/updateById")
    @ApiOperation(value = "修改productToUser",notes = "根据id修改productToUser")
    @ApiImplicitParam(paramType ="query",name = "productToUser",required = true)
    public void updateById(@RequestBody ProductToUser productToUser){
            productToUserService.save(productToUser);
    }


    @RequestMapping("/clearBySerialNumber")
    @ApiOperation(value = "清理productToUser",notes = "根据id清理productToUser")
    public void clearById(@RequestParam String serialNumber){
        productToUserService.clearBySerialNumber(serialNumber);
    }

    @RequestMapping("/clearByProductId")
    @ApiOperation(value = "清理productToUser",notes = "根据id清理productToUser")
    public void clearByProductId(@RequestParam Integer productId){
        productToUserService.clearByProductId(productId);
    }


    @RequestMapping("/getBySerialNumber")
    @ApiOperation(value = "清理productToUser",notes = "根据id清理productToUser")
    public ProductToUser getBySerialNumber(@RequestParam String serialNumber){
        return productToUserService.getBySerialNumber(serialNumber);
    }

}
