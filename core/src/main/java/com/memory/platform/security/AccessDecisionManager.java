package com.memory.platform.security;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.CollectionUtils;

public class AccessDecisionManager implements org.springframework.security.access.AccessDecisionManager {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessDecisionManager.class);

	@Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
		FilterInvocation fi = (FilterInvocation) object;
		logger.info("decide: {}", fi.getRequestUrl());
		//该请求所需权限:configAttributes
		//用户拥有权限:authentication.getAuthorities()
		if(!CollectionUtils.isEmpty(configAttributes) && !CollectionUtils.isEmpty(authentication.getAuthorities())){
			for(ConfigAttribute ca : configAttributes){
				if(StringUtils.isBlank(ca.getAttribute())){
					continue;
				}
				for(GrantedAuthority ga : authentication.getAuthorities()){
					if(ca.getAttribute().equals(ga.getAuthority())){
						return;
					}
				}
			}
		}
		throw new AccessDeniedException("");
    }

	@Override
    public boolean supports(ConfigAttribute attribute) {
		logger.info("supports attribute:{}", attribute.getAttribute());
	    return true;
    }

	@Override
    public boolean supports(Class<?> clazz) {
		logger.info(clazz.getName());
	    return FilterInvocation.class.equals(clazz);
    }

}
