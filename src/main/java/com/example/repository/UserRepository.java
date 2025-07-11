package com.example.repository;

import com.example.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends LdapRepository<User>, UserRepositoryCustom {

  Optional<User> findByUid(String uid);

  List<User> findBySn(String sn);
}
