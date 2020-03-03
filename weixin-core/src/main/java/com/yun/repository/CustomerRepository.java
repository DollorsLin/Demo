package com.yun.repository;

import com.yun.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository extends JpaRepository<Customer, Integer> , JpaSpecificationExecutor<Customer>{

    Customer findCustomerById(Integer id);
}