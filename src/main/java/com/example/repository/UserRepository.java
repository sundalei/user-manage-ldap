package com.example.repository;

import com.example.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends LdapRepository<User>, UserRepositoryCustom {

  /**
   * Finds a user by their UID.
   *
   * @param uid UID of the user
   * @return Optional containing the user if found, or an empty Optional otherwise
   */
  Optional<User> findByUid(String uid);

  /**
   * Finds users by their surname.
   *
   * @param sn Surname of the users
   * @return List of users with the given surname
   */
  List<User> findBySn(String sn);
}
