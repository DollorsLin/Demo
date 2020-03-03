package com.yun.repository;

import com.yun.pojo.Product;
import com.yun.pojo.ProductToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductToUserRepository extends JpaRepository<ProductToUser, Integer> , JpaSpecificationExecutor<ProductToUser>{

    ProductToUser findProductToUserById(Integer id);

    ProductToUser findProductToUserByProductIdAndPosition(Integer productId, Integer position);

    ProductToUser findProductToUserByProductId(Integer productId);

    ProductToUser findProductToUserByUserId(Integer userId);


}