package com.ssafy.ws.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ws.dto.User;
import com.ssafy.ws.model.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepo repo;
	
	@Autowired
	public UserServiceImpl(UserRepo repo) {
		this.repo = repo;
	}

	@Override
	public User select(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
