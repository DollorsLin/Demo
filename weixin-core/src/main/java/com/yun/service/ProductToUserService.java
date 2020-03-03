package com.yun.service;


import com.yun.mapper.ProductToUserMapper;
import com.yun.pojo.ProductToUser;
import com.yun.pojo.SerialNumber;
import com.yun.pojo.User;
import com.yun.repository.ProductToUserRepository;
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
 * 机台用户关联表
 */
@Service
public class ProductToUserService {

    @Autowired
    private ProductToUserRepository productToUserRepository;

    @Autowired
    private ProductToUserMapper productToUserMapper;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private UserService userService;

    public ProductToUser save(ProductToUser productToUser) {
        return productToUserRepository.save(productToUser);
    }

    public List<ProductToUser> list(Map<String, Object> params) {
        List<ProductToUser> list = productToUserRepository.findAll();
        return list;
    }

    public ProductToUser getById(Integer id) {
        return productToUserRepository.findProductToUserById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id : ids) {
            productToUserRepository.deleteById(id);
        }
    }

    public ProductToUser getByProductIdAndPosition(Integer productId, Integer position) {
        return productToUserRepository.findProductToUserByProductIdAndPosition(productId, position);
    }

    public ProductToUser getByOpenId(String openId) {
        return productToUserMapper.search(openId);
    }




    public void clearBySerialNumber(String serialNumber) {
        SerialNumber bySerialNumber = serialNumberService.findBySerialNumber(serialNumber);
        ProductToUser byUser = productToUserRepository.findProductToUserByProductIdAndPosition(bySerialNumber.getProductId(),1);
        if (byUser != null) {
            byUser.setUserId(null);
//            byUser.setQrcode(null);
//            byUser.setExpireTime(null);
            save(byUser);
        }
    }

    public void clearByProductId(Integer productId) {
        ProductToUser byUser = productToUserRepository.findProductToUserByProductIdAndPosition(productId,1);
        if (byUser != null) {
            byUser.setUserId(null);
            save(byUser);
        }
    }

    public ProductToUser getBySerialNumber(String serialNumber) {
        SerialNumber bySerialNumber = serialNumberService.findBySerialNumber(serialNumber);
        Integer productId = bySerialNumber.getProductId();
        return productToUserRepository.findProductToUserByProductId(productId);
    }
}

