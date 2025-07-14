package com.example.repository;

import com.example.entity.User;
import java.util.List;

public interface UserRepositoryCustom {

  /**
   * Finds users by their common name and surname.
   *
   * @param cn Common name of the users
   * @param sn Surname of the users
   * @return List of users with the given common name and surname
   */
  List<User> findByCnAndSn(String cn, String sn);
}
