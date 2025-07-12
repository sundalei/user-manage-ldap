package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** This class is a service for handling user-related business logic. */
@Service
public final class UserService {

  /** The user repository for database operations. */
  private final UserRepository userRepository;

  /**
   * This is the constructor for the UserService class.
   *
   * @param newUserRepo This is the user repository for handling user-related database operations.
   */
  public UserService(final UserRepository newRepo) {
    this.userRepository = newRepo;
  }

  /**
   * This method creates a new user.
   *
   * @param user This is the user to create.
   */
  public void createUser(final User user) {
    userRepository.save(user);
  }

  /**
   * This method finds all users.
   *
   * @return A list of all users.
   */
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  /**
   * This method finds a user by their uid.
   *
   * @param uid This is the user's uid.
   * @return An optional containing the user if found, or empty otherwise.
   */
  public Optional<User> findUserByUid(final String uid) {
    return userRepository.findByUid(uid);
  }

  /**
   * This method finds users by their surname.
   *
   * @param surname This is the user's surname.
   * @return A list of users with the given surname.
   */
  public List<User> findBySurname(final String surname) {
    return userRepository.findBySn(surname);
  }

  /**
   * This method finds users by their common name and surname.
   *
   * @param commonName This is the user's common name.
   * @param surname This is the user's surname.
   * @return A list of users with the given common name and surname.
   */
  public List<User> findByCnAndSn(final String commonName, final String surname) {
    return userRepository.findByCnAndSn(commonName, surname);
  }

  /**
   * This method updates a user.
   *
   * @param uid This is the uid of the user to update.
   * @param userUpdates This is the user object with the updated information.
   */
  public void updateUser(final String uid, final User userUpdates) {
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
   * This method deletes a user.
   *
   * @param uid This is the uid of the user to delete.
   */
  public void deleteUser(final String uid) {
    User userToDelete =
        userRepository
            .findByUid(uid)
            .orElseThrow(() -> new RuntimeException("User not found with uid: " + uid));
    userRepository.delete(userToDelete);
  }
}
