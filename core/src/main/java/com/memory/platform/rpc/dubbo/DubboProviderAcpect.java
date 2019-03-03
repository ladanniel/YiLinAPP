package com.memory.platform.rpc.dubbo;

import java.util.Arrays;

import org.apache.commons.lang.ObjectUtils.Null;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.RpcContext;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;

/*
 * 
 * byli 提供者拦截器 写入当前执行的账户信息
 * */
@Aspect
@Component
public class DubboProviderAcpect {
	
	@Around( "execution(* com.memory.platform.module.*.service.*.*(..)) ")
    public Object authority(ProceedingJoinPoint point) throws Throwable
    {
//	
//		  System.out.println("@Before：模拟权限检查...isConsumerSide : " + RpcContext.getContext().isConsumerSide() + " "
//		 		+ " isProviderSide:"  +  RpcContext.getContext().isProviderSide()
//		 		+ "");
//	        System.out.println("@Before：目标方法为：" + 
//	                point.getSignature().getDeclaringTypeName() + 
//	                "." + point.getSignature().getName());
//	        System.out.println("@Before：参数为：" + Arrays.toString(point.getArgs()));
//	        System.out.println("@Before：被织入的目标对象为：" + point.getTarget()); 
	        try {
	        	if(RpcContext.getContext().isProviderSide()) {
	        		if(RpcContext.getContext().getAttachment("user")==null) { 
	        			AppUtil.setThreadLocalObject("USER",null );
	        		} 
	        		 
	        		
	        	}
	        	Object ret =  point.proceed();
	        	if(RpcContext.getContext().isProviderSide()) { 
	        		
	        		//AppUtil.setThreadLocalObject("USER", null);
	        	}
	        	return ret ;
			} catch(Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
	        
	        finally{
				
			}
		 
	        
    }
}
