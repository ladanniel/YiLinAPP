package com.memory.platform.interceptor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.logging.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Resource;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Config;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.service.IResourceService;
import com.memory.platform.security.SpringContextUtil;
import com.memory.platform.security.annotation.LoginValidate;

public class SystemAuthorizeInterceptor implements HandlerInterceptor {

	@Autowired
	private IResourceService resourceService;
	Logger log = Logger.getLogger(SystemAuthorizeInterceptor.class);

	private boolean authValidation(String uri, List<Resource> resourceList) {

		for (Resource resource : resourceList) {
			if (resource.getUrl().equals(uri)) {
				return true;
			}
		}
		return false;// false
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object controller, ModelAndView model) throws Exception {
		// TODO Auto-generated method stub
		log.info(String.format("controller:%s view:%s",
				controller == null ? "null" : controller.toString(),
				model == null ? "null" : model.toString()));
		AppUtil.clearThreadMap();

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		// log.info(String.format("handler is  %s class id %s",
		// handler.toString(),handler.getClass().toString()));

		// 配置前台参数
		String WEB_PATH = AppUtil.getWebPath(request);// 项目路径
		request.setAttribute("WEB_PATH", WEB_PATH);
		request.setAttribute("PAGE_SIZE", Config.page_size);
		request.setAttribute("PAGE_LIST", Config.page_list);
		request.setAttribute("USER", UserUtil.getUser(request));
		AppUtil.setThreadLocalObject("USER", UserUtil.getUser(request));
		AppUtil.setThreadLocalObject("REQUEST", request);
		AppUtil.setThreadLocalObject("RESPONSE", response);
		AppUtil.setThreadLocalObject("SESSION", request.getSession());
		HandlerMethod method = (HandlerMethod) handler;
		;
		return preHandler(method);
	}

	/**
	 * @param method
	 */
	private boolean preHandler(HandlerMethod method) {
		boolean ret = true;
		boolean isReqLogin = false;
		LoginUrlAuthenticationEntryPoint loginAu = (LoginUrlAuthenticationEntryPoint) SpringContextUtil
				.getApplicationContext().getBean(
						"authenticationProcessingFilterEntryPoint");

		Authentication au = SecurityContextHolder.getContext()
				.getAuthentication();
		if (au != null) {
			for (GrantedAuthority res : au.getAuthorities()) {
				log.info(res.getAuthority());
			}
		}
	  
		LoginValidate classAnnotation = method.getBeanType()
				.getAnnotation(LoginValidate.class);
		LoginValidate methodAnnotation = method
				.getMethodAnnotation(LoginValidate.class);
		HttpServletRequest request = AppUtil
				.getThreadLocalObjectForKey("REQUEST");

		log.info(String.format("请求路由url:%s 控制器:%s", request.getRequestURI(),
				method.toString()));
		do {

			boolean isDefaultLogin = classAnnotation == null ? true
					: classAnnotation.value();
			if (methodAnnotation == null) {
				isReqLogin = isDefaultLogin;

			} else {
				isReqLogin = methodAnnotation.value();
			}
			// 登录验证
			if (isReqLogin) {

				Account account = AppUtil.getThreadLocalObjectForKey("USER");
				if (account == null) {
					throw new AccessDeniedException("权限错误");

				}
				ret = true;
				break;
			}
			// 不需要登录验证
			ret = true;
			// log.info(String.format("handler is  %s class id %s",
			// method.toString(),method.getClass().toString()));

		} while (false);

		return ret;
	}

}
