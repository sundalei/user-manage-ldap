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

	public List<User> findByCnAndSn(String commonName, String surname) {
		return userRepository.findByCnAndSn(commonName, surname);
	}

	public void updateUser(String uid, User userUpdates) {
		User existingUser = userRepository.findByUid(uid)
				.orElseThrow(() -> new RuntimeException("User not found with uid: " + uid));

		// Update attributes from the request
		existingUser.setCommonName(userUpdates.getCommonName());
		existingUser.setSn(userUpdates.getSn());
		existingUser.setUserPassword(userUpdates.getUserPassword());

		userRepository.save(existingUser);
	}

	public void deleteUser(String uid) {
		User userToDelete = userRepository.findByUid(uid)
				.orElseThrow(() -> new RuntimeException("User not found with uid: " + uid));
		userRepository.delete(userToDelete);
	}
}
