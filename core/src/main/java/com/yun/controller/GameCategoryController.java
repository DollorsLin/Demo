package com.yun.controller;

import com.google.gson.Gson;
import com.yun.pojo.GameCategory;
import com.yun.pojo.Place;
import com.yun.service.GameCategoryService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
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
@Api(value = "游戏类别", tags = {"游戏类别"})
@RequestMapping("/gameCategory")
public class GameCategoryController {

    @Autowired
    private GameCategoryService gameCategoryService;


    @RequestMapping(value = "/get", method = POST)
    @ApiOperation(value = "查询", notes = "查询")
    public GameCategory get(@RequestParam(value = "id") Integer id) {
        return gameCategoryService.get(id);
    }



    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增",notes = "新增")
    public JsonResult<GameCategory> add(@RequestBody GameCategory gameCategory){
        gameCategory.setAddTime(new Date());
        gameCategoryService.add(gameCategory);
        JsonResult ok = JsonResult.ok();
        return ok;
    }

    @RequestMapping(value = "/update", method = POST)
    @ApiOperation(value = "更新", notes = "更新")
    public void update(@RequestBody GameCategory gameCategory) {
        gameCategoryService.update(gameCategory);
    }

    @RequestMapping(value = "/list", method = POST)
    @ApiOperation(value = "查询所有")
    public JsonResult<List<GameCategory>> list() {
        List<GameCategory> list = gameCategoryService.list();
        JsonResult result = JsonResult.ok();
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/search", method = POST)
    @ApiOperation(value = "搜索")
    public JsonResult<PageUtils<GameCategory>> search(@RequestParam Map<String, Object> params) {
        PageUtils search = gameCategoryService.search(params);
        JsonResult result = JsonResult.ok();
        result.setData(search);
        return result;
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("serialNumber","aaaaa");
        map.put("position",1);
        map.put("createTime","2019-09-28 03:53:00");
        map.put("ticket",300);
        map.put("type",1);

        Gson gson = new Gson();
        String s = gson.toJson(map);
        System.out.println(s);


    }

}
