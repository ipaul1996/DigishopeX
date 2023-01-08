package com.ip.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ip.enums.UserType;
import com.ip.exception.AdminException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.model.Admin;
import com.ip.model.UserSession;
import com.ip.repository.AdminRepo;
import com.ip.repository.SessionRepo;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepo aRepo;
	
	@Autowired
	private SessionRepo sRepo;

	@Override
	public Admin createAdmin(Admin admin) throws AdminException {
		
		if(admin.getUserid() != null) {
			throw new AdminException("Id is auto generated, no need to provide it explicitly");
		}
		return aRepo.save(admin);	
	}

	
	@Override
	public Admin updateAdmin(Admin admin, String token) throws AdminException, CredentialException {
		
		if(admin.getUserid() == null) {
			throw new AdminException("Please provide admin id");
		}
		
		Optional<Admin> op = aRepo.findById(admin.getUserid());
		
		if(op.isEmpty()) {
			throw new AdminException("Invalid id...");
		}
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
		if(userSession.getUserid() != admin.getUserid()) {
			throw new AdminException("You are not authorized to perform this action...");
		}
		
		
		if(admin.getUserType() == UserType.CUSTOMER ) {
			throw new AdminException("Invalid user type");
		}
		
		if(admin.getAdminEmail() != null) {
			op.get().setAdminEmail(admin.getAdminEmail());
		}
		
		if(admin.getAdminMobile() != null) {
			op.get().setAdminMobile(admin.getAdminMobile());
		}
		
		if(admin.getAdminName() != null) {
			op.get().setAdminName(admin.getAdminName());
		}
		
		if(admin.getPassword() != null) {
			op.get().setPassword(admin.getPassword());
		}
		
		return aRepo.save(op.get());
		
	}

	
	@Override
	public Admin deleteAdmin(Integer adminId, String token) throws AdminException, CredentialException {
		
		Optional<Admin> op = aRepo.findById(adminId);
		
		if(op.isEmpty()) {
			throw new AdminException("Invalid id...");
		}
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
		if(userSession.getUserid() != adminId) {
			throw new AdminException("You are not authorized to perform this action...");
		}
		
		sRepo.delete(userSession);
		aRepo.delete(op.get());
				
		return op.get();
	}

}
