package com.yun.service;

import com.github.pagehelper.Page;
import com.yun.mapper.CustomerMapper;
import com.yun.pojo.Customer;
import com.yun.pojo.Place;
import com.yun.repository.CustomerRepository;
import com.yun.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 客户表
 *
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer get(Integer id) {
        return customerRepository.findCustomerById(id);
    }

}

