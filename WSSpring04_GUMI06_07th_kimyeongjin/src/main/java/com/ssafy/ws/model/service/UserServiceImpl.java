package com.ssafy.ws.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ws.dto.User;
import com.ssafy.ws.model.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo repo;
	
	public UserServiceImpl(UserRepo repo) {
		this.repo = repo;
	}

	@Override
	public User select(String id) {
		this.repo.select(id);
		return null;
	}

}
