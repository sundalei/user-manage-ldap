package com.example.repository;

import java.util.Optional;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;
import java.util.List;

@Repository
public interface UserRepository extends LdapRepository<User> {

    Optional<User> findByUid(String uid);

    List<User> findByCommonNameLikeIgnoreCase(String commonName);
    
    List<User> findBySn(String sn);
}
