package com.memory.platform.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.AccountDetails;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.module.system.service.impl.AccountService;
import com.memory.platform.module.system.service.impl.RoleService;



public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired 
	private AccountService userService;
	@Autowired 
	private RoleService roleService;
	
	@Override
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = userService.checkAccount(username);
	    if(account == null){
	    	return null;
	    }
	    List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
	    Role role = roleService.getRole(account.getRole().getId()); 
	    GrantedAuthority ga = new SimpleGrantedAuthority(role.getRole_code());
	    gas.add(ga); 
	    AccountDetails ud = new AccountDetails(username, account.getPassword(),account, true, true, true, true, gas);
	    ud.setAccount(account);
	    return ud;
    }

}
