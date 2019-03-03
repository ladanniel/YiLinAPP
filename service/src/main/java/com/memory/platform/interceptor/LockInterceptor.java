package com.memory.platform.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.locks.Lock;

import com.memory.platform.interceptor.LockType;

/*
 * 加锁 默认全局进程锁
 * 
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LockInterceptor {
	 LockType  value()  default LockType.global; 
	 String  lockName() default "";
	 
}
