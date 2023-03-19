package com.ip.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ip.dto.AdminDTO;
import com.ip.dto.AdminDTOV2;
import com.ip.enums.UserRole;
import com.ip.exception.AdminException;
import com.ip.model.Admin;
import com.ip.repository.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepo aRepo;
	
	@Autowired
	private PasswordEncoder pEncoder;

	@Override
	public Admin createAdmin(AdminDTO dto) throws AdminException {
		
		Admin admin = new Admin();
		
		admin.setAdminName(dto.getAdminName());
		admin.setEmail(dto.getAdminEmail());
		admin.setAdminMobile(dto.getAdminMobile());	
		admin.setPassword(pEncoder.encode(dto.getPassword()));
		
		if(!dto.getRole().toUpperCase().equals("ADMIN")) {
			throw new AdminException("Role should be admin");
		}
		
		admin.setRole(UserRole.ROLE_ADMIN);
	
		
		return aRepo.save(admin);	
	}

	
	@Override
	public Admin updateAdmin(AdminDTOV2 dto) throws AdminException {
		
		  
		String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Optional<Admin> op = aRepo.findByEmail(adminEmail);
		
		if(op.isEmpty()) {
			throw new AdminException("Admin details not found");
		}
		
		
		if(dto.getAdminMobile() != null) {
			op.get().setAdminMobile(dto.getAdminMobile());
		}
		
		if(dto.getAdminName() != null) {
			op.get().setAdminName(dto.getAdminName());
		}
		
		if(dto.getPassword() != null) {
			op.get().setPassword(pEncoder.encode(dto.getPassword()));
		}
		
		return aRepo.save(op.get());
		
	}

	
	@Override
	public Admin deleteAdmin() throws AdminException {
		
		String adminEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Optional<Admin> op = aRepo.findByEmail(adminEmail);
		
		if(op.isEmpty()) {
			throw new AdminException("No details found...");
		}
		
		aRepo.delete(op.get());
				
		return op.get();
	}

}
