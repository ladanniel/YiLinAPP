package com.memory.platform.interceptor;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.CapitalStatus;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Auth;
import com.memory.platform.global.UserUtil;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.zookeeper.ZooKeeperManager;

/*
 * by lil 
 * controller 执行时候的切面拦截器
 * */
@Component
@Aspect
public class ControllerAspect {
	@Autowired
	ZooKeeperManager zookeeperManager;
	@Autowired
	MemShardStrore memStore;

	public ControllerAspect() {

	}

	Logger logger = Logger.getLogger(ControllerAspect.class);

	@Around("execution(* com.memory.platform.module.app.*.controller.*.*(..))")
	public Object excuteControllerTime(ProceedingJoinPoint joinPoint)
			throws Throwable {
		return PrintExcuteTime(joinPoint);
	}

	@Around("execution(* com.memory.platform.module.*.controller.*.*(..))")
	public Object excuteControllerTimeWeb(ProceedingJoinPoint joinPoint)
			throws Throwable {
		return PrintExcuteTime(joinPoint);
	}

	private Object PrintExcuteTime(ProceedingJoinPoint joinPoint)
			throws Throwable {
		long oldTime = 0;
		if (logger.isInfoEnabled()) {
			oldTime = System.currentTimeMillis();
		}
		try {
			return joinPoint.proceed();
		} finally {

			if (oldTime != 0) {
				logger.info("controller excute time "
						+ (System.currentTimeMillis() - oldTime));
			}
		}
	}

	@Around("@annotation(lockInterceptor)  ")
	public Object excuteControllerMethod(ProceedingJoinPoint joinpoint,
			LockInterceptor lockInterceptor) throws Throwable {
		Runnable runRunnable = null;

		try {
			if (lockInterceptor.value() == LockType.global) {
				memStore.getLock().lock();
				runRunnable = new Runnable() {

					@Override
					public void run() {
						try {
							memStore.getLock().unlock();
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				};

			}

			Object obj = joinpoint.proceed();

			return obj;
		} finally {
			if (runRunnable != null) {
				runRunnable.run();
			}
		}

	}

	@Around("@annotation(authInterceptor)  ")
	public Object excuteControllerMethodAuth(ProceedingJoinPoint joinpoint,
			AuthInterceptor authInterceptor) throws Throwable {

		try {
			Account account = UserUtil.getAccount();
			if (authInterceptor.isAuth()) {

				if (account == null) {
				throw new BusinessException("登陆失效");
				}
				// 未认证
				else if (account.getAuthentication().equals(Auth.notapply)) {
					throw new BusinessException(authInterceptor.authErrCode(),
							authInterceptor.authErrMsg());
				}
				// 已提交申请
				else if (account.getAuthentication().equals(Auth.waitProcess)) {
					throw new BusinessException(authInterceptor.authWaitCode(),
							authInterceptor.authWaitMsg());
				}
				// 审核未通过
				else if (account.getAuthentication().equals(Auth.notAuth)) {
					throw new BusinessException(
							authInterceptor.authNotAuthCode(),
							authInterceptor.authNotAuthMsg());
				}

			}
			if (authInterceptor.capitalValid()) {
				if (account.getCapitalStatus() == CapitalStatus.notenable) {

					throw new BusinessException(
							authInterceptor.capitalNoOpenErrCode(),
							authInterceptor.capitalNoOpenErrMsg());
				} else if (account.getCapitalStatus() == CapitalStatus.close) {
					throw new BusinessException(
							authInterceptor.capitalCloseErrCode(),
							authInterceptor.capitalCloseErrMsg());
				}
			}
			Object obj = joinpoint.proceed();

			return obj;
		} finally {

		}

	}
}
