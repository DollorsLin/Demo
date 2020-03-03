package com.yun.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yun.pojo.User;
import com.yun.service.PlayDetailService;
import com.yun.service.RoleService;
import com.yun.service.UserService;
import com.yun.utils.JsonResult;
import com.yun.utils.PageUtils;
import com.yun.vo.DifficultyVO;
import com.yun.vo.PlayDetailVO;
import com.yun.vo.PlayMusicVO;
import com.yun.vo.UserToRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户表
 */
@RestController
@Api(value = "UserController")
@RequestMapping("/user")
@CrossOrigin
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PlayDetailService playDetailService;

//    /**
//     * 列表
//     */
//    @RequestMapping("/list")
//    @ApiOperation(value = "查询所有")
//    public JsonResult list(@RequestParam Map<String, Object> params){
//        List<User> list = userService.list(params);
//        JsonResult result = JsonResult.ok();
//        result.setData(list);
//        return result;
//    }


    @RequestMapping("/getById")
    @ApiOperation(value = "获取user",notes = "根据id获取user")
    public User getById(@RequestParam Integer id){
        return userService.getById(id);
    }

    @RequestMapping("/getByOpenId")
    @ApiOperation(value = "获取user",notes = "根据openId获取user")
    public User getByOpenId(@RequestParam(value = "openId") String openId){
        return userService.getByOpenId(openId);
    }


    @RequestMapping("/save")
    @ApiOperation(value = "新增user",notes = "新增user")
    @ApiImplicitParam(paramType ="query",name = "user",required = true)
    public User save(@RequestBody User user){
            return userService.save(user);
    }

    @RequestMapping("/updateById")
    @ApiOperation(value = "修改user",notes = "根据id修改user")
    @ApiImplicitParam(paramType ="query",name = "user",required = true)
    public void updateById(@RequestBody User user){
            userService.save(user);
    }


    @RequestMapping("/rankList")
    @ApiOperation(value = "排行榜",notes = "排行榜")
    public JsonResult rankList(@RequestBody Map<String, Object> params){
        return JsonResult.ok(userService.rankList(params));
    }



    @RequestMapping("/roleList")
    @ApiOperation(value = "角色列表",notes = "角色列表")
    public JsonResult roleList(){
        return JsonResult.ok(roleService.list());
    }

    @RequestMapping("/rank")
    @ApiOperation(value = "个人排行",notes = "个人排行")
    public JsonResult rank(@RequestParam Integer userId){
        List<PlayDetailVO> playDetailVOS = playDetailService.rank(userId);
        List<PlayMusicVO> playMusic = playDetailService.difficultyList(userId);

        for (PlayDetailVO playDetailVO : playDetailVOS) {
            playDetailVO.setDifficultyList(new ArrayList<>());
            for (PlayMusicVO playMusicVO : playMusic) {
                if (playMusicVO.getRoleId().equals(playDetailVO.getRoleId()) && playMusicVO.getSongId().equals(playDetailVO.getSongId())) {
                    DifficultyVO difficultyVO = new DifficultyVO();
                    difficultyVO.setDifficulty(playMusicVO.getDifficulty());
                    difficultyVO.setScore(playMusicVO.getScore());
                    playDetailVO.getDifficultyList().add(difficultyVO);
                }
            }
        }

        List<UserToRoleVO> rank = userService.rank(userId);
        for (UserToRoleVO userToRoleVO : rank) {
            userToRoleVO.setSongList(new ArrayList<>());
            for (PlayDetailVO playDetailVO : playDetailVOS) {
                if (userToRoleVO.getRoleId().equals(playDetailVO.getRoleId())) {
                    userToRoleVO.getSongList().add(playDetailVO);
                }
            }
        }
        return JsonResult.ok(rank);
    }

}
