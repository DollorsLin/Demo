package com.yun.controller;

import com.yun.pojo.ProductToken;
import com.yun.service.ProductTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "机台Token", tags = {"机台Token"})
@RequestMapping("/productToken")
public class ProductTokenController {

    @Autowired
    private ProductTokenService productTokenService;

    @RequestMapping(value = "/findProductTokenBySerialNumber", method = POST)
    @ApiOperation(value = "根据serialNumber获取ProductToken",notes = "根据serialNumber获取ProductToken")
    public ProductToken findProductTokenBySerialNumber(@RequestParam String serialNumber){
        ProductToken productTokenBySerialNumber = productTokenService.findProductTokenBySerialNumber(serialNumber);
        return productTokenBySerialNumber;
    }

    @RequestMapping(value = "/findProductTokenByToken", method = POST)
    @ApiOperation(value = "根据Token获取ProductToken",notes = "根据Token获取ProductToken")
    public ProductToken findProductTokenByToken(@RequestParam String token){
        ProductToken productTokenByToken = productTokenService.findProductTokenByToken(token);
        return productTokenByToken;
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "保存ProductToken",notes = "保存ProductToken")
    public void add(@RequestBody ProductToken productToken){
        productTokenService.save(productToken);
    }

    @RequestMapping(value = "/update", method = POST)
    @ApiOperation(value = "更新ProductToken",notes = "更新ProductToken")
    public void update(@RequestBody ProductToken productToken){
        productTokenService.save(productToken);
    }
}
