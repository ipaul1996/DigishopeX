package com.ip.service;

import com.ip.exception.AdminException;
import com.ip.exception.CredentialException;
import com.ip.model.Admin;

public interface AdminService {
	
	public Admin createAdmin(Admin admin) throws AdminException;
	
	public Admin updateAdmin(Admin admin, String token) throws AdminException, CredentialException;
	
	public Admin deleteAdmin(Integer adminId, String token) throws AdminException, CredentialException;

}
