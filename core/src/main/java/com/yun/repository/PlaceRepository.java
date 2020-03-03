package com.yun.repository;

import com.yun.pojo.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> , JpaSpecificationExecutor<Place>{

    Place findPlaceById(Integer id);

    List<Place> findPlacesByCustomerId(Integer customerId);
}