package com.yun.controller;

import com.yun.pojo.Customer;
import com.yun.service.CustomerService;
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
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "合作伙伴", tags = {"合作伙伴"})
@RequestMapping("/customer")
public class CustomerController{

    @Autowired
    private CustomerService customerService;



    @RequestMapping(value = "/get", method = POST)
    @ApiOperation(value = "查询", notes = "查询")
    public Customer get(@RequestParam(value = "id") Integer id) {
        return customerService.get(id);
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody Customer customer) {
        customer.setAddTime(new Date());
        customerService.add(customer);
    }

    @RequestMapping(value = "/update", method = POST)
    @ApiOperation(value = "更新", notes = "更新")
    public void update(@RequestBody Customer customer) {
        customerService.update(customer);
    }

    @RequestMapping(value = "/list", method = POST)
    @ApiOperation(value = "查询所有")
    public JsonResult<List<Customer>> list(){
        List<Customer> list = customerService.list();
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<Customer>> search(@RequestParam Map<String, String> keys) {
        PageUtils search = customerService.search(keys);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }

    @RequestMapping(value = "/delete", method = POST)
    @ApiOperation(value = "删除", notes = "删除")
    public void delete(@RequestParam Integer customerId) {
        customerService.delete(customerId);
    }


    @RequestMapping(value = "/check", method = POST)
    @ApiOperation(value = "查询该客户下有无场地和机台")
    public List<Customer> check(@RequestParam Integer customerId){
        return customerService.check(customerId);
    }
}
