package com.yun.utils.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "配置数据")
public class ConfigData {
    //        游戏类别、版本号、加密狗编号、最大离线时长、升级包类型（1-程序包、2-资源包）


    /**
     * 机台类别编码
     */
    @ApiModelProperty(value = "机台类别编码")
    private Integer categoryId;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version;

    /**
     * 加密狗提供的串号列表，逗号隔开
     */
    @ApiModelProperty(value = "加密狗提供的串号列表，逗号隔开")
    private String serialNumbers;

    /**
     * 离线最大天数，0则不限
     */
    @ApiModelProperty(value = "离线最大天数，0则不限")
    private Integer offlineDays;

    /**
     * 包类型: 1 程序安装包，2 资源安装包
     */
    @ApiModelProperty(value = "包类型: 1 程序安装包，2 资源安装包")
    private Integer packageType;

    /**
     * 生成时间 单位（秒）
     */
    @ApiModelProperty(value = "生成时间")
    private Integer createDate;
}
