package com.yun.service;


import com.yun.pojo.UpdatePackageToProduct;
import com.yun.repository.UpdatePackageToProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 升级包与机台关系
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-22 15:38:02
 */
@Service
public class UpdatePackageToProductService {

    @Autowired
    private UpdatePackageToProductRepository updatePackageToProductRepository;

    public UpdatePackageToProduct findTopBySerialNumberOrderByAddTimeDesc(String serialNumber) {
        return updatePackageToProductRepository.findTopBySerialNumberOrderByIdDesc(serialNumber);
    }

//    public UpdatePackageToProduct findTopByProductIdOrderByAddTimeDesc(Integer productId) {
//        return updatePackageToProductRepository.findTopByProductIdOrderByAddTimeDesc(productId);
//    }

    public void add(UpdatePackageToProduct updatePackageToProduct) {
        updatePackageToProductRepository.save(updatePackageToProduct);
    }

    public void addList(List<UpdatePackageToProduct> updatePackageToProducts) {
        updatePackageToProductRepository.saveAll(updatePackageToProducts);
    }


}

