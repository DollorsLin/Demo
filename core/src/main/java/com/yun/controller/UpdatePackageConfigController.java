package com.yun.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yun.constant.Constants;
import com.yun.pojo.Product;
import com.yun.pojo.SerialNumber;
import com.yun.pojo.UpdatePackage;
import com.yun.pojo.UpdatePackageConfig;
import com.yun.service.ProductService;
import com.yun.service.SerialNumberService;
import com.yun.service.UpdatePackageConfigService;
import com.yun.service.UpdatePackageService;
import com.yun.utils.JsonResult;
import com.yun.utils.config.MD5Utils;
import com.yun.utils.config.Config;
import com.yun.utils.config.ConfigData;
import com.yun.vo.UpdatePackageConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "升级包配置", tags = {"升级包配置"})
@RequestMapping("/updatePackageConfig")
public class UpdatePackageConfigController {



    @Autowired
    private UpdatePackageService updatePackageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private UpdatePackageConfigService updatePackageConfigService;


    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody UpdatePackageConfig updatePackageConfig) {
        updatePackageConfig.setAddTime(new Date());
        updatePackageConfig.setIsDeleted(0);
        updatePackageConfigService.add(updatePackageConfig);
    }


    private String serialNumbers( List<SerialNumber> serialNumbers){
        String serialNumbersStr = "";
        for(SerialNumber serialNumber : serialNumbers){
            String serialNumber1 = serialNumber.getSerialNumber();
            serialNumbersStr += serialNumber1 + ",";
        }
        return serialNumbersStr;
    }

    @RequestMapping(value = "/getConfigList", method = POST)
    @ApiOperation(value = "更新包和授权文件列表", notes = "更新包和授权文件列表")
    public List<UpdatePackageConfigVO> getConfigList(@RequestParam String serialNumber) throws Exception {

        SerialNumber bySerialNumber = serialNumberService.findBySerialNumber(serialNumber);

        Integer offlineDays = bySerialNumber.getOfflineDays();
        Integer categoryId = bySerialNumber.getCategoryId();
        Integer version = bySerialNumber.getVersion();
        Integer productCategoryId = bySerialNumber.getProductCategoryId();
        Integer manualUpdatePackageId = bySerialNumber.getManualUpdatePackageId();

        List<UpdatePackage> updatePackages = updatePackageService.getNewPackage(categoryId, version, productCategoryId);
        if(Objects.nonNull(manualUpdatePackageId) && manualUpdatePackageId > 0){
            UpdatePackage updatePackage = updatePackageService.get(manualUpdatePackageId);
            updatePackages.add(updatePackage);
        }

        List<UpdatePackageConfigVO> list = new ArrayList<>();
        //生成配置文件
        for (UpdatePackage updatePackage : updatePackages) {
            UpdatePackageConfigVO updatePackageVO = updatePackageConfigService.configVO(updatePackage, serialNumber, offlineDays);
            list.add(updatePackageVO);
        }
        return list;
    }






    public static void main(String[] args) throws Exception {
        ConfigData configData = new ConfigData();
        configData.setCategoryId(1000);
        configData.setPackageType(1);
        configData.setSerialNumbers("********");
        configData.setVersion(20060);
        configData.setOfflineDays(0);
        configData.setCreateDate((int) (System.currentTimeMillis() / 1000));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String configDataStr = gson.toJson(configData);
        System.out.println(configDataStr);

        Config config = new Config(configDataStr);
        byte[] config111 = config.getConfig();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmSSS");
        String format = dateFormat.format(date);
        String fileName = "JingLingBall_" + format + ".shlic";
        FileUtils.writeByteArrayToFile(new File("C:\\Users\\kimti\\Desktop\\" + fileName), config111);
    }



}
