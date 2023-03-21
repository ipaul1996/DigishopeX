package com.ip.service;

import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
	
	public String loginIntoAccount();

	public String logoutFromAccount(HttpServletRequest request);
}
