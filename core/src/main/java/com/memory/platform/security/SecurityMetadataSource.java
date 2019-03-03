package com.memory.platform.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.CollectionUtils;

import com.memory.platform.global.EHCacheUtil;
import com.memory.platform.module.system.service.impl.MenuService;
import com.memory.platform.module.system.service.impl.RoleService;

public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static final Logger logger = LoggerFactory.getLogger(SecurityMetadataSource.class);

	private static Map<String, Collection<ConfigAttribute>> RESOURCE_ROLE_MAP = null;

	private static Collection<ConfigAttribute> ALL_CONFIG_ATTRIBUTES = null;

	private static List<ConfigAttribute> ALL_CONFIG_ATTRIBUTES_LIST = null;

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	// 计算哪些角色具有该资源的权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		logger.info(object.getClass().getName());
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getRequestUrl();
		url = url.substring(0, url.indexOf("?")>-1?url.indexOf("?"):url.length());
		// System.out.println("什么鬼======"+url);
		return getResourceRoleMap().get(url);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		logger.info("getAllConfigAttributes");
		if (ALL_CONFIG_ATTRIBUTES == null) {
			ALL_CONFIG_ATTRIBUTES = getAllConfigAttributesMap();
		}
		return ALL_CONFIG_ATTRIBUTES;
	}

	@SuppressWarnings("unchecked")
	private List<ConfigAttribute> getAllConfigAttributesMap() {
		EHCacheUtil.initCacheManager(EHCacheUtil.ehcachePath);  
        EHCacheUtil.initCache("rolelistsecurity");  
        ALL_CONFIG_ATTRIBUTES_LIST = (List<ConfigAttribute>) EHCacheUtil.get("ALL_CONFIG_ATTRIBUTES_LIST");
        if(ALL_CONFIG_ATTRIBUTES_LIST == null){
        	ALL_CONFIG_ATTRIBUTES_LIST = new ArrayList<ConfigAttribute>();
		    List<String> roles = roleService.getRoleListSecurity();
			if (!CollectionUtils.isEmpty(roles)) {
				for (String role : roles) {
					ConfigAttribute ca = new SecurityConfig(role);
					ALL_CONFIG_ATTRIBUTES_LIST.add(ca);
				}
			}
			EHCacheUtil.initCacheManager(EHCacheUtil.ehcachePath);  
		    EHCacheUtil.initCache("rolelistsecurity");  
		    EHCacheUtil.put("ALL_CONFIG_ATTRIBUTES_LIST", ALL_CONFIG_ATTRIBUTES_LIST);  
		}
		return ALL_CONFIG_ATTRIBUTES_LIST; 
	}

	@SuppressWarnings("unchecked")
	private Map<String, Collection<ConfigAttribute>> getResourceRoleMap() {
		EHCacheUtil.initCacheManager();  
        EHCacheUtil.initCache("rolelistsecurity");  
        RESOURCE_ROLE_MAP = (Map<String, Collection<ConfigAttribute>>) EHCacheUtil.get("RESOURCE_ROLE_MAP");
        if(RESOURCE_ROLE_MAP == null){
        	RESOURCE_ROLE_MAP = new HashMap<String, Collection<ConfigAttribute>>();
			List<Map<String, Object>> menus = menuService.getMenuOrCode();
			if (!CollectionUtils.isEmpty(menus)) {
				for (Map<String, Object> map: menus) {
					if (StringUtils.isBlank(map.get("url").toString())) {
						continue;
					}
					Collection<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();
					String[] roles = map.get("role_codes").toString().split(",");
					if (roles.length > 0) {
						for (String string : roles) {
							ConfigAttribute ca = new SecurityConfig(string);;
							cas.add(ca);
						}
					}
					RESOURCE_ROLE_MAP.put(map.get("url").toString(), cas);
				}
			}
			EHCacheUtil.initCacheManager(EHCacheUtil.ehcachePath);  
		    EHCacheUtil.initCache("rolelistsecurity");  
		    EHCacheUtil.put("RESOURCE_ROLE_MAP", RESOURCE_ROLE_MAP);  
        }
		return RESOURCE_ROLE_MAP;
	}

	public void clear() {
		RESOURCE_ROLE_MAP = null;
		ALL_CONFIG_ATTRIBUTES = null;
		ALL_CONFIG_ATTRIBUTES_LIST = null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		logger.info(clazz.getName());
		return FilterInvocation.class.equals(clazz);
	}

}
