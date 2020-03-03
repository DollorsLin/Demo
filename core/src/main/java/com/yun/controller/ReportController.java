package com.yun.controller;


import com.yun.pojo.Score;
import com.yun.pojo.SerialNumber;
import com.yun.service.ReportService;
import com.yun.service.ScoreService;
import com.yun.service.SerialNumberService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.ScoreVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "统计", tags = {"统计"})
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private SerialNumberService serialNumberService;







    @RequestMapping(value = "/getTotal", method = POST)
    @ApiOperation(value = "总统计")
    public JsonResult getTotal(@RequestParam Map<String, Object> keys) {
        return reportService.getTotal(keys);
    }


    @RequestMapping(value = "/getDetail", method = POST)
    @ApiOperation(value = "每日统计")
    public JsonResult getDetail(@RequestParam Map<String, Object> keys) {

        return JsonResult.ok(reportService.getDetail(keys));
    }



}
