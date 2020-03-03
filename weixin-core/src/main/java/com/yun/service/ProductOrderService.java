package com.yun.service;

import com.yun.pojo.ProductOrder;
import com.yun.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 机台订单关联表
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-12-20 15:40:13
 */
@Service
public class ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public ProductOrder save(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    public List<ProductOrder> list( Map<String, Object> params){
        List<ProductOrder> list = productOrderRepository.findAll();
        return list;
    }

    public ProductOrder getFirstProductOrder(Integer productId) {
        return productOrderRepository.findFirstByProductIdOrderByAddTimeDesc(productId);
    }

    public ProductOrder getById(Integer id) {
        return productOrderRepository.findProductOrderById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            productOrderRepository.deleteById(id);
        }
    }

    public ProductOrder getByOrderId(Integer orderId) {
        return productOrderRepository.findProductOrderByRelateId(orderId);

    }
}

