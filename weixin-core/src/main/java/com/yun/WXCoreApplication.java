package com.yun;

import com.yun.task.TaskExecutorUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yun.mapper")
public class WXCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WXCoreApplication.class, args);

		//处理异步任务
		TaskExecutorUtils.getInstance().init();
	}

}
