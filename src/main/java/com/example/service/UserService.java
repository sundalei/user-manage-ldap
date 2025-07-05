package com.example.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void createUser(User user) {
		userRepository.save(user);
	}

	public List<User> findAllUsers() {
        return userRepository.findAll();
	}

	public Optional<User> findUserByUid(String uid) {
		return userRepository.findByUid(uid);
	}

	public List<User> findBySurname(String surname) {
		return userRepository.findBySn(surname);
	}
}
