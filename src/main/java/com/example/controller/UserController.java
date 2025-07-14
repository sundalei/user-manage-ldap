package com.example.controller;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.service.UserService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public final class UserController {

  /** Service to handle user-related operations. */
  private final UserService userService;

  /**
   * Constructor to initialize UserController with UserService.
   *
   * @param userService Service to handle user-related operations
   */
  public UserController(final UserService userService) {
    this.userService = userService;
  }

  /**
   * Creates a new user.
   *
   * @param userDTO Data Transfer Object containing user information
   */
  @PostMapping
  public void createUser(final @RequestBody UserDTO userDTO) {
    userService.createUser(convertToUser(userDTO));
  }

  /**
   * Retrieves all users.
   *
   * @return List of UserDTO containing user information
   */
  @GetMapping
  public List<UserDTO> findAllUsers() {
    List<User> users = userService.findAllUsers();
    return users.stream().map(this::convertToUserDTO).collect(Collectors.toList());
  }

  /**
   * Retrieves a user by UID.
   *
   * @param uid UID of the user to retrieve
   * @return UserDTO containing user information
   */
  @GetMapping("/{uid}")
  public UserDTO findUserByUid(final @PathVariable String uid) {
    User user =
        userService
            .findUserByUid(uid)
            .orElseThrow(() -> new RuntimeException("User not found with uid: " + uid));

    return convertToUserDTO(user);
  }

  /**
   * Retrieves users by surname.
   *
   * @param surname Surname of the users to retrieve
   * @return List of UserDTO containing user information
   */
  @GetMapping("/surname/{surname}")
  public List<UserDTO> findBySurname(final @PathVariable String surname) {
    return userService.findBySurname(surname).stream()
        .map(this::convertToUserDTO)
        .collect(Collectors.toList());
  }

  /**
   * Retrieves users by common name and surname.
   *
   * @param commonName Common name of the users to retrieve
   * @param surname Surname of the users to retrieve
   * @return List of UserDTO containing user information
   */
  @GetMapping("/cn/{commonName}/sn/{surname}")
  public List<UserDTO> findByCnAndSn(
      final @PathVariable String commonName, final @PathVariable String surname) {
    return userService.findByCnAndSn(commonName, surname).stream()
        .map(this::convertToUserDTO)
        .collect(Collectors.toList());
  }

  /**
   * Updates a user by UID.
   *
   * @param uid UID of the user to update
   * @param userDTO Data Transfer Object containing updated user information
   */
  @PutMapping("/{uid}")
  public void updateUser(final @PathVariable String uid, final @RequestBody UserDTO userDTO) {
    userService.updateUser(uid, convertToUser(userDTO));
  }

  /**
   * Deletes a user by UID.
   *
   * @param uid UID of the user to delete
   */
  @DeleteMapping("/{uid}")
  public void deleteUser(final @PathVariable String uid) {
    userService.deleteUser(uid);
  }

  /**
   * Converts a User entity to a UserDTO Data Transfer Object.
   *
   * @param user User entity to convert
   * @return UserDTO containing user information
   */
  private UserDTO convertToUserDTO(final User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setCommonName(user.getCommonName());
    userDTO.setSn(user.getSn());
    userDTO.setUid(user.getUid());
    userDTO.setUserPassword(new String(user.getUserPassword(), StandardCharsets.UTF_8));
    return userDTO;
  }

  /**
   * Converts a UserDTO Data Transfer Object to a User entity.
   *
   * @param userDTO Data Transfer Object to convert
   * @return User entity containing user information
   */
  private User convertToUser(UserDTO userDTO) {
    User user = new User();
    user.setCommonName(userDTO.getCommonName());
    user.setSn(userDTO.getSn());
    user.setUid(userDTO.getUid());
    user.setUserPassword(
        userDTO.getUserPassword().getBytes(java.nio.charset.StandardCharsets.UTF_8));
    return user;
  }
}
