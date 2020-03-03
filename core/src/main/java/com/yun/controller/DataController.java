package com.yun.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.yun.service.*;
import com.yun.utils.JsonResult;
import com.yun.vo.Revenue;
import com.yun.vo.RevenueDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import oracle.jrockit.jfr.events.RequestableEventEnvironment;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "列表", tags = {"列表"})
@RequestMapping("/data")
public class DataController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private ScoreService scoreService;



    @RequestMapping(value = "/getCustomerList", method = GET)
    @ApiOperation(value = "获取客户列表")
    public Object getCustomerList(@RequestParam Integer categoryId) {
        return customerService.getCustomerList(categoryId);
    }


    @RequestMapping(value = "/getPlaceList", method = GET)
    @ApiOperation(value = "获取场地列表")
    public Object getPlaceList(@RequestParam Integer categoryId, @RequestParam Integer customerId) {
        return placeService.getPlaceList(categoryId, customerId);
    }

    @RequestMapping(value = "/report", method = GET)
    public Object getTotal(@RequestParam Map<String, Object> params) {
        Object o1 = params.get("type");
        Object o2 = params.get("categoryId");
        Integer categoryId = Integer.valueOf(o2.toString());
        if (Objects.nonNull(o1)) {
            Integer type = Integer.valueOf(o1.toString());
            switch (type) {
                case 1:
                    if (categoryId == 4) {
                        return scoreService.getTotalOne(params);
                    } else if (categoryId == 2){
                        List<Revenue> totalTwo = scoreService.getTotalTwo(params);
                        List<Revenue> order = scoreService.getOrder(params);
                        for (Revenue revenue : totalTwo) {
                            revenue.set元("-");
                            for (Revenue revenue1 : order) {
                                if (revenue.getId().equals(revenue1.getId())) {
                                    revenue.set元(revenue1.get元());
                                }
                            }
                        }
                        return totalTwo;
                    } else {
                        return scoreService.getTotal(params);
                    }
                case 2:
                    return scoreService.songTotal(params);
                case 3:
                    return scoreService.scanTotal(params);
                case 4:
                    return JsonResult.ok();
            }
        }
        return JsonResult.ok();
    }

    @RequestMapping(value = "/detail", method = GET)
    public Object getGetail(@RequestParam Map<String, Object> params) {
        Object o1 = params.get("type");
        Object o2 = params.get("categoryId");
        Integer categoryId = Integer.valueOf(o2.toString());
        if (Objects.nonNull(o1)) {
            Integer type = Integer.valueOf(o1.toString());
            switch (type) {
                case 1:
                    if (categoryId == 4) {
                        return scoreService.getDetailOne(params);
                    } else if (categoryId == 2){
                        Map<Date, Date> map = new HashMap<>();
                        List<RevenueDetail> detailTwo = scoreService.getDetailTwo(params);
                        List<RevenueDetail> orderDetail = scoreService.getOrderDetail(params);
                        List<RevenueDetail> a = new ArrayList<>();
                        for (RevenueDetail revenueDetail : detailTwo) {
                            map.put(revenueDetail.get时间(),revenueDetail.get时间());
                        }
                        for (RevenueDetail revenueDetail : orderDetail) {
                            map.put(revenueDetail.get时间(),revenueDetail.get时间());
                        }

                        Collection<Date> values = map.values();

                        for (Date value : values) {
                            RevenueDetail revenueDetail = new RevenueDetail();
                            revenueDetail.set时间(value);
                            revenueDetail.set元("-");
                            revenueDetail.set投币("-");
                            a.add(revenueDetail);
                        }
                        for (RevenueDetail revenueDetail : a) {
                            for (RevenueDetail revenueDetail1 : detailTwo) {
                                if (revenueDetail.get时间().equals(revenueDetail1.get时间())) {
                                    revenueDetail.set投币(revenueDetail1.get投币());
                                }
                            }
                        }
                        for (RevenueDetail revenueDetail : a) {
                            for (RevenueDetail detail : orderDetail) {
                                if (revenueDetail.get时间().equals(detail.get时间())) {
                                    revenueDetail.set元(detail.get元());
                                }
                            }
                        }

                        Collections.sort(a,(o11, o21) -> o11.get时间().compareTo(o21.get时间()));
                        return a;
                    } else {
                        return scoreService.getDetail(params);
                    }
                case 2:
                    return scoreService.getSongDetail(params);
                case 3:
                    return scoreService.getScanDetail(params);
                case 4:
                    return JsonResult.ok();
            }
        }
        return JsonResult.ok();
    }



}
