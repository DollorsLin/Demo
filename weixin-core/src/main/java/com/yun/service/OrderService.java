package com.yun.service;


import com.yun.pojo.Order;
import com.yun.repository.OrderRepository;
import com.yun.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-12-12 13:59:20
 */
@Service
public class  OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> list( Map<String, Object> params){
        List<Order> list = orderRepository.findAll();
        return list;
    }


    public Order signLastOrder(Integer productId){
        return orderRepository.findFirstByProductIdOrderByIdDesc(productId);
    }

    public Order getById(Integer id) {
        return orderRepository.findOrderById(id);
    }

    public Order getByOtherNo(String otherNo) {
        return orderRepository.findOrderByOtherOrderNo(otherNo);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            orderRepository.deleteById(id);
        }
    }
}

