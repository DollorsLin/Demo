package com.yun.repository;

import com.yun.pojo.SerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SerialNumberRepository extends JpaRepository<SerialNumber, Integer>, JpaSpecificationExecutor<SerialNumber> {

    SerialNumber findSerialNumberById(Integer id);

    List<SerialNumber> findByProductId(Integer productId);

    SerialNumber findBySerialNumber(String serialNumber);

    List<SerialNumber> findBySerialId(Integer productId);

}