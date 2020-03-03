package com.yun.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yun.constant.Constants;
import com.yun.mapper.UpdatePackageConfigMapper;
import com.yun.pojo.UpdatePackage;
import com.yun.pojo.UpdatePackageConfig;
import com.yun.repository.UpdatePackageConfigRepository;
import com.yun.utils.config.Config;
import com.yun.utils.config.ConfigData;
import com.yun.utils.config.MD5Utils;
import com.yun.vo.UpdatePackageConfigVO;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 升级包配置
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class UpdatePackageConfigService {

    @Autowired
    private UpdatePackageConfigMapper updatePackageConfigMapper;

    @Autowired
    private UpdatePackageConfigRepository updatePackageConfigRepository;


    @Value("${file.path}")
    private String localFilePath;

    @Value("${server.url}")
    private String serverUrl;

    @Data
    class ConfigReturn {
        public String md5;
        public String path;
    }


    public UpdatePackageConfig findByUpdatePakcageIdAndSerialNumber(Integer updatePackageId, String serialNumber) {
        return updatePackageConfigMapper.get(updatePackageId, serialNumber);
    }

    public UpdatePackageConfig add(UpdatePackageConfig updatePackageConfig) {
        UpdatePackageConfig save = updatePackageConfigRepository.save(updatePackageConfig);
        return save;
    }

    public void delete(Integer updatePackageId) {
        UpdatePackageConfig byUpdatePackageId = updatePackageConfigRepository.findByUpdatePackageId(updatePackageId);
        byUpdatePackageId.setIsDeleted(Constants.DELETED_1);
        updatePackageConfigRepository.save(byUpdatePackageId);
    }


    public UpdatePackageConfigVO configVO(UpdatePackage updatePackage, String serialNumbers, Integer offlineDays) throws Exception {
        Integer updatePackageId = updatePackage.getId();
        Integer categoryId = updatePackage.getCategoryId();
        String filePath = updatePackage.getFilePath();
        Integer version = updatePackage.getVersion();
        Integer packageType = updatePackage.getPackageType();
        String packageMd5 = updatePackage.getMd5();

        UpdatePackageConfigVO configVO = new UpdatePackageConfigVO();
        configVO.setId(updatePackageId);
        configVO.setCategoryId(categoryId);
        configVO.setUpdatePackagePath(filePath);
        configVO.setVersion(version);
        configVO.setPackageType(packageType);
        configVO.setUpdatePackageMd5(packageMd5);

        UpdatePackageConfig updatePakcageConfig = findByUpdatePakcageIdAndSerialNumber(updatePackageId, serialNumbers);
        if (Objects.isNull(updatePakcageConfig)) {
            ConfigReturn configReturn = ConfigReturn(categoryId, packageType, serialNumbers, version, offlineDays);
            updatePakcageConfig = addUpdatePackageConfig(serialNumbers, updatePackageId, configReturn.getMd5(), configReturn.getPath());
        }
        configVO.setConfigPath(updatePakcageConfig.getPath());
        configVO.setConfigMd5(updatePakcageConfig.getMd5());
        return configVO;
    }

    private UpdatePackageConfig addUpdatePackageConfig(String serialNumber, Integer updatePackageId, String configMd5, String configPath) {
        UpdatePackageConfig updatePakcageConfig = new UpdatePackageConfig();
        updatePakcageConfig.setAddTime(new Date());
        updatePakcageConfig.setIsDeleted(0);
        updatePakcageConfig.setMd5(configMd5);
        updatePakcageConfig.setPath(configPath);
        updatePakcageConfig.setSerialNumbers(serialNumber);
        updatePakcageConfig.setUpdatePackageId(updatePackageId);
        UpdatePackageConfig add = add(updatePakcageConfig);
        return add;
    }


    private ConfigReturn ConfigReturn(Integer categoryId, Integer packageType, String serialNumbers, Integer version, Integer offlineDays) throws Exception {
        ConfigData configData = new ConfigData();
        configData.setCategoryId(categoryId);
        configData.setPackageType(packageType);
        configData.setSerialNumbers(serialNumbers);
        configData.setVersion(version);
        configData.setOfflineDays(offlineDays);
        configData.setCreateDate((int) (System.currentTimeMillis() / 1000));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String configDataStr = gson.toJson(configData);
        Config config = new Config(configDataStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String format = dateFormat.format(date);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = "sh_" + uuid + "_" + format + ".shlic";
        String path = localFilePath + "config/" + fileName;
        File file = new File(path);
        FileUtils.writeByteArrayToFile(file, config.getConfig());
        String configMd5 = MD5Utils.getMd5(path);
        String configPath = serverUrl + "config/" + fileName;

        ConfigReturn configReturn = new ConfigReturn();
        configReturn.setMd5(configMd5);
        configReturn.setPath(configPath);
        return configReturn;
    }

}

