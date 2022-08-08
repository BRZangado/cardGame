package com.letscode.cardgame.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SystemUser extends User {

	private static final long serialVersionUID = 1L;

	private String name;

	public SystemUser(String name, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		
		this.name = name;
	}

	public String getName() {
		return name;
	}
}