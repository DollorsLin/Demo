package com.yun.service;

import com.yun.pojo.ProductCategory;
import com.yun.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 产品分类表
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class  ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> list(){
        List<ProductCategory> list = productCategoryRepository.findAll();
        return list;
    }

    public void add(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public void update(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }


    public ProductCategory get(Integer id){
        return productCategoryRepository.findProductCategoryById(id);
    }

    public List<ProductCategory> findProductCategoryBySerialId(Integer serialId){
        List<ProductCategory> productCategoryBySerialId = productCategoryRepository.findProductCategoryBySerialId(serialId);
        return productCategoryBySerialId;
    }
}

