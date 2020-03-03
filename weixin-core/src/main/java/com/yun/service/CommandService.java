package com.yun.service;

import com.yun.constant.Constants;
import com.yun.pojo.Command;
import com.yun.repository.CommandRepository;
import com.yun.utils.JsonUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 命令
 *
 * @author kiki
 * @email 459987463@qq.com
 * @date 2019-07-15 21:48:37
 */
@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;

    public Command get(Integer id) {
        return commandRepository.findCommandById(id);
    }

    public void add(Command command) {
        commandRepository.save(command);
    }
    public void update(Command command) {
        commandRepository.save(command);
    }

    public List<Command> findCommandBySerialNumber(String serialNumber) {
        List<Command> commands = commandRepository.findCommandBySerialNumberAndIsDeleted(serialNumber, Constants.DELETED_0);
        return commands;
    }

    public void addCommand(String serialNumber, Integer productId, Map<String, Object> map) {
        Command command = new Command();
        command.setSerialNumber(serialNumber);
        command.setProductId(productId);
        command.setIsDeleted(0);
        command.setAddTime(new Date());
        command.setCommand(100);
        command.setParameters(JsonUtils.toString(map));
        commandRepository.save(command);
    }

    public void addCommand(String serialNumber, Integer productId, Integer type, String url) {
        Command command = new Command();
        command.setProductId(productId);
        command.setSerialNumber(serialNumber);
        command.setIsDeleted(0);
        command.setAddTime(new Date());
        command.setCommand(100);
        Map<String, Object> map = new HashMap<>();
        map.put("type","1004");
        command.setParameters(JsonUtils.toString(map));
        commandRepository.save(command);
    }
}

