package com.thupx.tms.domain;

import java.io.Serializable;
import java.util.List;

public class UserFull implements Serializable {
	private UserExtra extra;
	private List<Authority> auth;
	public UserFull(UserExtra extra, List<Authority> auth) {
		super();
		this.extra = extra;
		this.auth = auth;
	}
	public UserExtra getExtra() {
		return extra;
	}
	public void setExtra(UserExtra extra) {
		this.extra = extra;
	}
	public List<Authority> getAuth() {
		return auth;
	}
	public void setAuth(List<Authority> auth) {
		this.auth = auth;
	}
	
	
	
	
}
