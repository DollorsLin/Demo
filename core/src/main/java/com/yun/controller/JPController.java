package com.yun.controller;


import com.yun.pojo.Jp;
import com.yun.service.JPService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.JpVO;
import com.yun.vo.ScoreVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@Api(value = "jp大奖", tags = {"jp大奖"})
@RequestMapping("/jp")
public class JPController {

    @Autowired
    JPService jpService;

    @RequestMapping(value = "/addList", method = POST)
    @ApiOperation(value = "新增组", notes = "新增组")
    public void addList(@RequestBody List<Jp> jps) {
        for(Jp jp : jps){
            jp.setAddTime(new Date());
        }
        jpService.saveAll(jps);
    }

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody Jp jp) {
        jp.setAddTime(new Date());
        jpService.add(jp);
    }

    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<JpVO>> search(@RequestParam Map<String, Object> keys) {
        PageUtils search = jpService.search(keys);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }
}
