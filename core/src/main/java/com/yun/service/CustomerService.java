package com.yun.service;

import com.github.pagehelper.Page;
import com.yun.mapper.CustomerMapper;
import com.yun.pojo.Customer;
import com.yun.pojo.Place;
import com.yun.repository.CustomerRepository;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 客户表
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;


    public Customer get(Integer id) {
        return customerRepository.findCustomerById(id);
    }

    public void add(Customer customer) {
        customerRepository.save(customer);
    }

    public void update(Customer customer) {
        customerRepository.save(customer);
    }


    public List<Customer> list() {
        List<Customer> list = customerRepository.findAll();
        return list;
    }

    public PageUtils search(Map<String, String> params) {
        Integer page = Integer.parseInt(params.get("page"));
        Integer limit = Integer.parseInt(params.get("limit"));
        Page<Place> result = startPage(page, limit);
        customerMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
    }

    public void delete(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

    public List<Customer> check(Integer customerId) {
        List<Customer> list = customerMapper.check(customerId);
        return list;
    }

    public Object getCustomerList(Integer categoryId) {
        return customerMapper.getCustomerList(categoryId);
    }
}

