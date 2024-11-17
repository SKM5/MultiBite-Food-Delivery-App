package com.multibite.service;

import com.multibite.exception.AdminException;
import com.multibite.model.Admin;

public interface AdminService {

	public String createNewAdmin() throws AdminException;

	public String createUser(String userType, Object user) throws AdminException;

	public String updateUser(String userType, Object user) throws AdminException;

	public String deactivateUser(String userType, Object user) throws AdminException;
	


}
