package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.entity.User;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.naming.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.test.context.ActiveProfiles;

@DataLdapTest
@ActiveProfiles("test")
public class UserRepositoryIT {

  @Autowired private UserRepository userRepository;

  @Test
  void testFindAll() {
    Iterable<User> users = userRepository.findAll();
    assertThat(users).hasSize(2);
  }

  @Test
  void testFindByUid() {
    Optional<User> userOptional = userRepository.findByUid("testuser");
    assertThat(userOptional).isPresent();
    User user = userOptional.get();
    assertThat(user.getCommonName()).isEqualTo("Test User");
    assertThat(user.getSn()).isEqualTo("User");
  }

  @Test
  void testSaveAndFindById() {
    User newUser = new User();
    newUser.setUid("newuser");
    newUser.setCommonName("New User");
    newUser.setSn("New");
    newUser.setUserPassword("newpassword".getBytes(StandardCharsets.UTF_8));

    Name dn = LdapNameBuilder.newInstance().add("uid", "newuser").build();
    newUser.setDn(dn);

    userRepository.save(newUser);

    Optional<User> savedUserOptional = userRepository.findById(newUser.getDn());
    assertThat(savedUserOptional).isPresent();
    assertThat(savedUserOptional.get().getCommonName()).isEqualTo("New User");

    // Cleanup
    userRepository.delete(newUser);
  }

  @Test
  void testUpdate() {
    User userToUpdate = userRepository.findByUid("anotheruser").orElseThrow();
    userToUpdate.setSn("UpdatedSurname");
    userRepository.save(userToUpdate);

    User updatedUser = userRepository.findByUid("anotheruser").orElseThrow();
    assertThat(updatedUser.getSn()).isEqualTo("UpdatedSurname");
  }

  @Test
  void testDelete() {
    User userToDelete = userRepository.findByUid("testuser").orElseThrow();
    userRepository.delete(userToDelete);

    Optional<User> deletedUser = userRepository.findByUid("testuser");
    assertThat(deletedUser).isNotPresent();
  }
}
