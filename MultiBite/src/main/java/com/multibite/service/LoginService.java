package com.multibite.service;

import com.multibite.exception.LoginException;
import com.multibite.exception.RestaurantOwnerException;
import com.multibite.model.*;


public interface LoginService {

	public String loginAccount(LoginDTO loginDTO) throws LoginException, RestaurantOwnerException;

	public String logoutAccount(String role, String key) throws LoginException;

}
