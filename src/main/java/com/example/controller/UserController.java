package com.example.controller;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{uid}")
    public Optional<UserDTO> findUserByUid(@PathVariable String uid) {
        Optional<User> user = userService.findUserByUid(uid);
        return user.map(this::convertToUserDTO);
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setCommonName(user.getCommonName());
        userDTO.setSn(user.getSn());
        userDTO.setUid(user.getUid());
        userDTO.setUserPassword(UserDTO.asciiValuesToString(user.getUserPassword()));
        return userDTO;
    }

    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setCommonName(userDTO.getCommonName());
        user.setSn(userDTO.getSn());
        user.setUid(userDTO.getUid());
        user.setUserPassword(userDTO.getUserPassword());
        return user;
    }

}
