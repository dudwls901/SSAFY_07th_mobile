package com.ssafy.ws.model.repo;

import com.ssafy.ws.dto.User;

public interface UserRepo {
	
	User select(String id);
	
}
