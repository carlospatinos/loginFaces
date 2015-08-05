package com.gapsi.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "loginBean")
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 7480483082729505751L;

	private String name;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

}
