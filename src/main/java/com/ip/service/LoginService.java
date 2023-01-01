package com.ip.service;

import com.ip.dto.LoginDTO;
import com.ip.exception.AdminException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;

public interface LoginService {
	
	public String loginIntoAccount(LoginDTO logindto) throws CredentialException, AdminException, CustomerException;

	public String logoutFromAccount(String key) throws CredentialException;
}
