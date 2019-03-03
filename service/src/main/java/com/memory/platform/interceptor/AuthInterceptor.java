package com.memory.platform.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 认证
 * */
@Target(value = { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthInterceptor {
	boolean isAuth() default true; // 是否需要认证

	boolean capitalValid() default false;// 资金账户认证 认证是否开户了资金

	String capitalNoOpenErrCode() default "884";// 未开通

	String capitalNoOpenErrMsg() default "您未开通资金账户,是否要去开通？";

	String capitalCloseErrCode() default "883";// 关闭

	String capitalCloseErrMsg() default "您的资金账户已被冻结,具体原因请联系管理员!";

	String authErrCode() default "885"; // 认证错误码

	String authErrMsg() default "该功能需要认证,您是否要去认证？"; // 认证错误提示信息

	String authWaitCode() default "886";

	String authWaitMsg() default "您已提交申请，请耐心等待审核."; // 认证错误提示信息

	String authNotAuthCode() default "887";

	String authNotAuthMsg() default "审核未通过，请重新提交认证信息。"; // 认证错误提示信息
}
