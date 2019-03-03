package com.memory.platform.entity.sys;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;

public class UserDetails extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = -7694894659502722966L;
    
    private User user;

	public UserDetails(String username, 
			String id, 
			User user,
			boolean enabled, 
			boolean accountNonExpired,
            boolean credentialsNonExpired, 
            boolean accountNonLocked, 
            Collection<? extends GrantedAuthority> authorities) {
	    super(username, id,enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
