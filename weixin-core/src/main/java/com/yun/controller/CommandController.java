package com.yun.controller;

import com.yun.constant.Constants;
import com.yun.pojo.Command;
import com.yun.service.CommandService;
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
@Api(value = "命令", tags = {"命令"})
@RequestMapping("/command")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @RequestMapping(value = "/add", method = POST)
    @ApiOperation(value = "新增", notes = "新增")
    public void add(@RequestBody Command command) {
        command.setAddTime(new Date());
        commandService.add(command);
    }

    @RequestMapping(value = "/deleteById", method = POST)
    @ApiOperation(value = "删除", notes = "删除")
    public void deleteById(@RequestParam Integer id) {
        Command command = commandService.get(id);
        command.setIsDeleted(Constants.DELETED_1);
        commandService.update(command);
    }


    @RequestMapping(value = "/findCommandBySerialNumber", method = POST)
    @ApiOperation(value = "根据serialNumber查找", notes = "根据serialNumber查找")
    public List<Command> findCommandBySerialNumber(@RequestParam(value = "SerialNumber") String serialNumber){
        return commandService.findCommandBySerialNumber(serialNumber);
    }



}
