package com.yun.service;


import com.github.pagehelper.Page;
import com.yun.constant.Constants;
import com.yun.mapper.UpdatePackageMapper;
import com.yun.pojo.UpdatePackage;
import com.yun.repository.UpdatePackageRepository;
import com.yun.utils.PageUtils;
import com.yun.vo.UpdatePackageVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 升级包
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class UpdatePackageService {

    @Autowired
    private UpdatePackageRepository updatePackageRepository;

    @Autowired
    private UpdatePackageMapper updatePackageMapper;

    public UpdatePackage get(Integer id) {
        return updatePackageRepository.findUpdatePackageById(id);
    }

    public void add(UpdatePackage updatePackage) {
        updatePackageRepository.save(updatePackage);
    }

    public void update(UpdatePackage updatePackage) {
        updatePackageRepository.save(updatePackage);
    }

    public void delete(Integer id) {
        UpdatePackage updatePackageById = updatePackageRepository.findUpdatePackageById(id);
        updatePackageById.setIsDeleted(Constants.DELETED_1);
        updatePackageRepository.save(updatePackageById);
    }

    public PageUtils search(Map<String, String> params) {
        Integer page = Integer.parseInt(params.get("page"));
        Integer limit = Integer.parseInt(params.get("limit"));
        Page<UpdatePackageVO> result = startPage(page, limit);
        updatePackageMapper.search(params);
        PageUtils pageUtils = new PageUtils(result);
        return pageUtils;
    }

    public List<UpdatePackage> findByCategoryId(Integer categoryId){
        List<UpdatePackage> byCategoryId = updatePackageRepository.findByCategoryId(categoryId);
        return byCategoryId;
    }

    public List<UpdatePackage> findByProductCategoryId(Integer categoryId){
        List<UpdatePackage> byCategoryId = updatePackageRepository.findByProductCategoryId(categoryId);
        return byCategoryId;
    }

    public List<UpdatePackage> getNewPackage(Integer categoryId, Integer version, Integer productCategoryId){
        List<UpdatePackage> gameUpdateByCategoryIdAndVersion = updatePackageMapper.getNewPackage(categoryId, version, productCategoryId);
        return gameUpdateByCategoryIdAndVersion;
    }

//    public List<UpdatePackage> findResourseUpdateByCategoryIdAndVersion(Integer categoryId, Integer version){
//        List<UpdatePackage> gameUpdateByCategoryIdAndVersion = updatePackageMapper.findResourseUpdateByCategoryIdAndVersion(categoryId, version);
//        return gameUpdateByCategoryIdAndVersion;
//    }


    public List<UpdatePackageVO> findNewManualUpdatePackage(Integer productCategoryId) {
        List<UpdatePackageVO> updatePackages = updatePackageMapper.findNewManualUpdatePackage(productCategoryId);
        return updatePackages;
    }

//    public UpdatePackage findNewGamePackage(Integer categoryId) {
//        UpdatePackage newUpdatePackage = updatePackageMapper.findNewGamePackage(categoryId);
//        return newUpdatePackage;
//    }

    public Integer countUpdatePackage(Integer categoryId, Integer version, Integer productCategoryId) {
        Integer integer = updatePackageMapper.countUpdatePackage(categoryId, version, productCategoryId);
        return integer;
    }


}

