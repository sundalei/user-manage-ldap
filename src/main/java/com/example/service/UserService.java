package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class UserService {

  /** user repository */
  private final UserRepository userRepository;

  /**
   * Constructor to initialize UserService with UserRepository.
   *
   * @param userRepository Repository to handle user data operations
   */
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Creates a new user.
   *
   * @param user User entity containing user information
   */
  public void createUser(User user) {
    userRepository.save(user);
  }

  /**
   * Retrieves all users.
   *
   * @return List of User entities containing user information
   */
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Retrieves a user by UID.
   *
   * @param uid UID of the user to retrieve
   * @return Optional containing User entity if found, empty otherwise
   */
  public Optional<User> findUserByUid(String uid) {
    return userRepository.findByUid(uid);
  }

  /**
   * Retrieves users by surname.
   *
   * @param surname Surname of the users to retrieve
   * @return List of User entities matching the surname
   */
  public List<User> findBySurname(String surname) {
    return userRepository.findBySn(surname);
  }

  /**
   * Retrieves users by common name and surname.
   *
   * @param commonName Common name of the users to retrieve
   * @param surname Surname of the users to retrieve
   * @return List of User entities matching the common name and surname
   */
  public List<User> findByCnAndSn(String commonName, String surname) {
    return userRepository.findByCnAndSn(commonName, surname);
  }

  /**
   * Updates an existing user by UID.
   *
   * @param uid UID of the user to update
   * @param userUpdates User entity containing updated user information
   */
  public void updateUser(String uid, User userUpdates) {
    User existingUser =
        userRepository
            .findByUid(uid)
            .orElseThrow(() -> new RuntimeException("User not found with uid: " + uid));

    // Update attributes from the request
    existingUser.setCommonName(userUpdates.getCommonName());
    existingUser.setSn(userUpdates.getSn());
    existingUser.setUserPassword(userUpdates.getUserPassword());

    userRepository.save(existingUser);
  }

  /**
   * Deletes a user by UID.
   *
   * @param uid UID of the user to delete
   */
  public void deleteUser(String uid) {
    User userToDelete =
        userRepository
            .findByUid(uid)
            .orElseThrow(() -> new RuntimeException("User not found with uid: " + uid));
    userRepository.delete(userToDelete);
  }
}
