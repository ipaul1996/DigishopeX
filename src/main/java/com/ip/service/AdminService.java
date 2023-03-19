package com.ip.service;

import com.ip.dto.AdminDTO;
import com.ip.dto.AdminDTOV2;
import com.ip.exception.AdminException;
import com.ip.model.Admin;

public interface AdminService {
	
	public Admin createAdmin(AdminDTO dto) throws AdminException;
	
	public Admin updateAdmin(AdminDTOV2 dto) throws AdminException ;
	
	public Admin deleteAdmin() throws AdminException ;

}
