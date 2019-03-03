package com.memory.platform.entity.sys;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;

import com.memory.platform.entity.member.Account;

public class AccountDetails extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = -7694894659502722966L;
    
    private Account account;

	public AccountDetails(String username, 
			String id, 
			Account account,
			boolean enabled, 
			boolean accountNonExpired,
            boolean credentialsNonExpired, 
            boolean accountNonLocked, 
            Collection<? extends GrantedAuthority> authorities) {
	    super(username, id,enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	 
}
