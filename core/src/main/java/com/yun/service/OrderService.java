package com.yun.service;


import com.github.pagehelper.Page;
import com.yun.mapper.OrderMapper;
import com.yun.pojo.Order;
import com.yun.repository.OrderRepository;
import com.yun.utils.PageUtils;
import com.yun.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 订单
 *
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public PageUtils list(Map<String, Object> params){
        Integer page = Integer.parseInt(params.get("page").toString());
        Integer limit = Integer.parseInt(params.get("limit").toString());
        Page<OrderVO> result = startPage(page, limit);
        orderMapper.list(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
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

    public List<OrderVO> excelOrder(Map<String, Object> params) {
        List<OrderVO> list = orderMapper.list(params);
        return list;

    }
}

