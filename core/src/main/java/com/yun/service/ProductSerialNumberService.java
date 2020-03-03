package com.yun.service;

import com.yun.constant.Constants;
import com.yun.pojo.ProductSerialNumber;
import com.yun.repository.ProductSerialNumberRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 机台加密狗
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class ProductSerialNumberService {

    @Autowired
    private ProductSerialNumberRepository productSerialNumberRepository;

    public List<ProductSerialNumber> findByProductId(Integer productId){
        return productSerialNumberRepository.findByProductIdAndIsDeleted(productId, Constants.DELETED_0);
    }

    public ProductSerialNumber findBySerialNumber(String serialNumber){
        return productSerialNumberRepository.findBySerialNumber(serialNumber);
    }

    public void add(@RequestBody ProductSerialNumber productSerialNumber){
        productSerialNumberRepository.save(productSerialNumber);
    }

    public void update(@RequestBody ProductSerialNumber productSerialNumber){
        productSerialNumberRepository.save(productSerialNumber);
    }

    public void delete(@RequestParam(value = "id") Integer id){
        ProductSerialNumber productSerialNumberById = productSerialNumberRepository.findProductSerialNumberById(id);
        productSerialNumberById.setIsDeleted(Constants.DELETED_1);
        productSerialNumberRepository.save(productSerialNumberById);
    }

}

