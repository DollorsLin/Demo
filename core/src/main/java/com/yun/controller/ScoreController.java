package com.yun.controller;


import com.yun.pojo.Product;
import com.yun.pojo.Score;
import com.yun.pojo.SerialNumber;
import com.yun.service.ProductService;
import com.yun.service.ScoreService;
import com.yun.service.SerialNumberService;
import com.yun.service.SerialService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.DetailVO;
import com.yun.vo.ScoreVO;
import com.yun.vo.StatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "机台分数", tags = {"机台分数"})
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private SerialNumberService serialNumberService;


    @RequestMapping(value = "/addList", method = POST)
    @ApiOperation(value = "新增组", notes = "新增组")
    public void addList(@RequestBody List<Score> scores, @RequestParam(value = "serialNumber") String serialNumber) {

        SerialNumber bySerialNumber = serialNumberService.findBySerialNumber(serialNumber);
        Integer productId = bySerialNumber.getProductId();
        for (Score score : scores) {
            score.setAddTime(new Date());
            score.setProductId(productId);
            score.setSerialNumber(serialNumber);
        }
        //TODO 去重
        List<Score> list = new ArrayList<>();
        for (Score score : scores) {
            Score score1 = scoreService.findScore(score.getProductId(), score.getSerialNumber(), score.getPosition(), score.getCreateTime());
            if (score1 == null) {
                list.add(score);
            }
        }

        scoreService.addScores(list);
    }


    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody Score score) {
        score.setAddTime(new Date());
        scoreService.add(score);
    }

    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<ScoreVO>> search(@RequestParam Map<String, Object> keys) {
        PageUtils search = scoreService.search(keys);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }

    @RequestMapping(value = "/statistics", method = POST)
    @ApiOperation(value = "总统计")
    public JsonResult statistics(@RequestParam Map<String, Object> keys) {
        return scoreService.statistics(keys);
    }


    @RequestMapping(value = "/detail", method = POST)
    @ApiOperation(value = "每日统计")
    public JsonResult detail(@RequestParam Map<String, Object> keys) {

        return JsonResult.ok(scoreService.detail(keys));
    }



}
