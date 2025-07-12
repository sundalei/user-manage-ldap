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
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public void createUser(@RequestBody UserDTO userDTO) {
    userService.createUser(convertToUser(userDTO));
  }

  @GetMapping
  public List<UserDTO> findAllUsers() {
    List<User> users = userService.findAllUsers();
    return users.stream().map(this::convertToUserDTO).collect(Collectors.toList());
  }

  @GetMapping("/{uid}")
  public UserDTO findUserByUid(@PathVariable String uid) {
    User user =
        userService
            .findUserByUid(uid)
            .orElseThrow(() -> new RuntimeException("User not found with uid: " + uid));

    return convertToUserDTO(user);
  }

  @GetMapping("/surname/{surname}")
  public List<UserDTO> findBySurname(@PathVariable String surname) {
    return userService.findBySurname(surname).stream()
        .map(this::convertToUserDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/cn/{commonName}/sn/{surname}")
  public List<UserDTO> findByCnAndSn(
      @PathVariable String commonName, @PathVariable String surname) {
    return userService.findByCnAndSn(commonName, surname).stream()
        .map(this::convertToUserDTO)
        .collect(Collectors.toList());
  }

  @PutMapping("/{uid}")
  public void updateUser(@PathVariable String uid, @RequestBody UserDTO userDTO) {
    userService.updateUser(uid, convertToUser(userDTO));
  }

  @DeleteMapping("/{uid}")
  public void deleteUser(@PathVariable String uid) {
    userService.deleteUser(uid);
  }

  private UserDTO convertToUserDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setCommonName(user.getCommonName());
    userDTO.setSn(user.getSn());
    userDTO.setUid(user.getUid());
    userDTO.setUserPassword(new String(user.getUserPassword(), StandardCharsets.UTF_8));
    return userDTO;
  }

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
