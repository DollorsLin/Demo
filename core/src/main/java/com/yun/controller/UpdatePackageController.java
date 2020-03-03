package com.yun.controller;

import com.yun.pojo.*;
import com.yun.service.UpdatePackageConfigService;
import com.yun.service.UpdatePackageService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.UpdatePackageConfigVO;
import com.yun.vo.UpdatePackageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "升级包", tags = {"升级包"})
@RequestMapping("/updatePackage")
public class UpdatePackageController {

    @Autowired
    private UpdatePackageService updatePackageService;

    @Autowired
    private UpdatePackageConfigService updatePackageConfigService;

    @RequestMapping(value = "/get", method = POST)
    @ApiOperation(value = "查询", notes = "查询")
    public UpdatePackage get(@RequestParam(value = "id") Integer id) {
        return updatePackageService.get(id);
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody UpdatePackage updatePackage) {
        updatePackage.setAddTime(new Date());
        updatePackage.setIsDeleted(0);
        updatePackageService.add(updatePackage);
    }

    @RequestMapping(value = "/update", method = POST)
    @ApiOperation(value = "更新", notes = "更新")
    public void update(@RequestBody UpdatePackage updatePackage) {
        updatePackageService.update(updatePackage);
    }

    @RequestMapping(value = "/delete", method = POST)
    @ApiOperation(value = "删除", notes = "删除")
    public void delete(@RequestParam(value = "id") Integer id) {
        updatePackageService.delete(id);
    }

    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<UpdatePackageVO>> search(@RequestParam Map<String, String> keys) {
        PageUtils search = updatePackageService.search(keys);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }

    @RequestMapping(value = "/findByCategoryId", method = POST)
    @ApiOperation(value = "根据类别ID查询", notes = "根据类别ID查询")
    public JsonResult<List<UpdatePackage>> findByCategoryId(@RequestParam Integer categoryId) {
        List<UpdatePackage> byCategoryId = updatePackageService.findByCategoryId(categoryId);
        JsonResult result = JsonResult.ok();
        result.setData(byCategoryId);
        return result;
    }


    @RequestMapping(value = "/authorize", method = POST)
    @ApiOperation(value = "后台生成授权文件",notes = "后台生成授权文件")
    JsonResult authorize( @RequestParam Integer id, @RequestParam String serialNumbers, @RequestParam Integer offlineDays ) throws Exception {
        UpdatePackage updatePackage = updatePackageService.get(id);
        UpdatePackageConfigVO updatePackageVO = updatePackageConfigService.configVO(updatePackage, serialNumbers, offlineDays);
        JsonResult ok = JsonResult.ok();
        ok.setData(updatePackageVO);
        return ok;
    }

    @RequestMapping(value = "/findByProductCategoryId", method = POST)
    @ApiOperation(value = "根据产品类别Id查找游戏包", notes = "根据产品类别Id查找游戏包")
    public List<UpdatePackage> findByProductCategoryId(@RequestParam Integer productCategoryId) {
        List<UpdatePackage> updatePackages = updatePackageService.findByProductCategoryId(productCategoryId);
        return updatePackages;
    }

//    @RequestMapping(value = "/findGameUpdateByCategoryIdAndVersion", method = POST)
//    @ApiOperation(value = "查找游戏包", notes = "查找游戏包")
//    public UpdatePackage findGameUpdateByCategoryIdAndVersion(@RequestParam Integer categoryId, Integer version) {
//        UpdatePackage byCategoryId = updatePackageService.findGameUpdateByCategoryIdAndVersion(categoryId, version);
//        return byCategoryId;
//    }
//
//    @RequestMapping(value = "/findResourseUpdateByCategoryIdAndVersion", method = POST)
//    @ApiOperation(value = "查找游戏资源包", notes = "查找游戏资源包")
//    public List<UpdatePackage> findResourseUpdateByCategoryIdAndVersion(@RequestParam Integer categoryId, Integer version) {
//        List<UpdatePackage> byCategoryId = updatePackageService.findResourseUpdateByCategoryIdAndVersion(categoryId, version);
//        return byCategoryId;
//    }



    /**
     * @param productCategoryId
     * @return
     */
    @RequestMapping(value = "/findNewManualUpdatePackage", method = POST)
    @ApiOperation(value = "查找最新手动更新包", notes = "查找最新手动更新包")
    public List<UpdatePackageVO> findNewManualUpdatePackage(@RequestParam Integer productCategoryId) {
        List<UpdatePackageVO> updatePackages = updatePackageService.findNewManualUpdatePackage(productCategoryId);
        return updatePackages;
    }
//
//    /**
//     * 使用在升级游戏
//     * @param categoryId
//     * @return
//     */
//    @RequestMapping(value = "/findNewGamePackage", method = POST)
//    @ApiOperation(value = "查找最新游戏包", notes = "查找最新游戏包")
//    public UpdatePackage findNewGamePackage(@RequestParam Integer categoryId) {
//        UpdatePackage byCategoryId = updatePackageService.findNewGamePackage(categoryId);
//        return byCategoryId;
//    }

    @RequestMapping(value = "/countUpdatePackage", method = POST)
    @ApiOperation(value = "是否有自动更新包", notes = "是否有自动更新包")
    Integer countUpdatePackage(@RequestParam Integer categoryId, @RequestParam Integer version, @RequestParam Integer productCategoryId){
        Integer integer = updatePackageService.countUpdatePackage(categoryId, version, productCategoryId);
        return integer;
    }


}
