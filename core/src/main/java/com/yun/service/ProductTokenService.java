package com.yun.service;

import com.yun.pojo.ProductToken;
import com.yun.repository.ProductTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 机台Token
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 20:00:33
 */
@Service
public class  ProductTokenService {

    @Autowired
    private ProductTokenRepository productTokenRepository;

    public ProductToken save(ProductToken productToken) {
        return productTokenRepository.save(productToken);
    }

    public ProductToken findProductTokenBySerialNumber(String serialNumber) {
        return productTokenRepository.findProductTokenBySerialNumber(serialNumber);
    }

    public ProductToken findProductTokenByToken(String token){
        return productTokenRepository.findProductTokenByToken(token);
    }
}

