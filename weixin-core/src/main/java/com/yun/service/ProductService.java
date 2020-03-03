package com.yun.service;


import com.yun.pojo.Product;
import com.yun.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 产品表
 *
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;





    public Product get(Integer id){
        return productRepository.findProductById(id);
    }


    public void orderingUpdate(Product product) {
        productRepository.save(product);
    }






}

