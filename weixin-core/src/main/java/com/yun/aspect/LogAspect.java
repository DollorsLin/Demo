/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yun.aspect;

import com.google.gson.Gson;
import com.yun.annotation.OperatorLogAnnotation;
import com.yun.pojo.OperatorLog;
import com.yun.service.OperatorLogService;
import com.yun.utils.HttpContextUtils;
import com.yun.utils.IPUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
public class LogAspect {

	@Autowired
	private OperatorLogService operatorLogService;
	
	@Pointcut("@annotation(com.yun.annotation.OperatorLogAnnotation)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveLog(point, time);

		return result;
	}

	private void saveLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		OperatorLog operatorLog = new OperatorLog();
		OperatorLogAnnotation log = method.getAnnotation(OperatorLogAnnotation.class);
		if(log != null){
			//注解上的描述
			operatorLog.setOperation(log.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		operatorLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args);
			operatorLog.setParams(params);
		}catch (Exception e){
			e.printStackTrace();
		}
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		operatorLog.setIp(IPUtils.getIpAddr(request));
		String username = request.getHeader("username");
		operatorLog.setOperation(username);
		operatorLog.setTime(time);
		operatorLog.setAddTime(new Date());

		//TODO线程
		operatorLogService.save(operatorLog);
	}
}
