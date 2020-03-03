package com.yun.service;

import com.yun.constant.Constants;
import com.yun.pojo.Command;
import com.yun.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Command findCommandByProductId(Integer productId) {
        return commandRepository.findFirstByProductIdAndCommandOrderByAddTimeDesc(productId, 100);
    }
}

