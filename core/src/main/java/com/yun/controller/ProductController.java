package com.yun.controller;

import com.yun.pojo.Product;
import com.yun.pojo.SerialNumber;
import com.yun.service.SerialNumberService;
import com.yun.service.ProductService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "机台", tags = {"机台"})
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SerialNumberService serialNumberService;

    @RequestMapping(value = "/get", method = POST)
    @ApiOperation(value = "查询", notes = "查询")
    public Product get(@RequestParam(value = "id") Integer id) {
        return productService.get(id);
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增",notes = "新增")
    public JsonResult<Product> add(@RequestBody Product product){
        product.setAddTime(new Date());
        Product save = productService.save(product);
        JsonResult ok = JsonResult.ok();
        ok.setData(save);
        return ok;
    }

    @RequestMapping(value = "/update", method = POST)
    @ApiOperation(value = "更新", notes = "更新")
    public void update(@RequestBody Product product) {
        productService.update(product);
    }

    @RequestMapping(value = "/setting", method = POST)
    @ApiOperation(value = "机台设置", notes = "机台设置")
    public void setting(@RequestBody Product product) {
        productService.setting(product);
    }


    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<ProductVO>> search(@RequestParam Map<String, Object> params) {
        PageUtils search = productService.search(params);
        List list = search.getList();

        List<ProductVO> productVOS = new ArrayList<>();
        for(Object object: list){
            if(Objects.nonNull(object)){
                ProductVO productVO = (ProductVO) object;
                Integer productId = productVO.getId();
                List<SerialNumber> byProductId = serialNumberService.findByProductId(productId);
                productVO.setSerialNumberList(byProductId);
                productVOS.add(productVO);
            }
        }
        search.setList(productVOS);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }


    @RequestMapping(value = "/findBySerialNumber", method = POST)
    @ApiOperation(value = "根据唯一码查询",notes = "根据唯一码查询")
    public Product findBySerialNumber(@RequestParam String serialNumber){
        SerialNumber bySerialNumber = serialNumberService.findBySerialNumber(serialNumber);
        Integer productId = bySerialNumber.getProductId();
        Product productBySerialNumber = productService.get(productId);
        return productBySerialNumber;
    }


    @RequestMapping(value = "/deleteByTime", method = POST)
    @ApiOperation(value = "通过删除分数表和JP表的数据")
    public JsonResult deleteByTime(@RequestParam Map<String, Object> params) {
        productService.deleteByTime(params);
        return JsonResult.ok();
    }

    @RequestMapping(value = "/findByPlaceId", method = POST)
    @ApiOperation(value = "根据场地id查询",notes = "根据场地id查询")
    public List<Product>  findByPlaceId(@RequestParam Integer placeId){
        return productService.findByPlaceId(placeId);
    }


//    @RequestMapping(value = "/checkOnline", method = POST)
    @Scheduled(cron = "*/30 * 9-23 * * ?")
    @ApiOperation(value = "检查机台是否在线")
    public void checkOnline() {
        productService.checkOnline();
    }


}
