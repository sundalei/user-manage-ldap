package com.example.repository.impl;

import com.example.entity.User;
import com.example.repository.UserRepositoryCustom;
import java.util.List;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;

public final class UserRepositoryImpl implements UserRepositoryCustom {

  /**
   * Constructs a new UserRepositoryImpl with the given LdapTemplate.
   *
   * @param ldapTemplate LdapTemplate for LDAP operations
   */
  private final LdapTemplate ldapTemplate;

  /**
   * Constructs a new UserRepositoryImpl with the given LdapTemplate.
   *
   * @param ldapTemplate LdapTemplate for LDAP operations
   */
  public UserRepositoryImpl(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  @Override
  public List<User> findByCnAndSn(String cn, String sn) {
    LdapQuery query =
        LdapQueryBuilder.query()
            .where("objectclass")
            .is("person")
            .and("cn")
            .is(cn)
            .and("sn")
            .is(sn);
    return ldapTemplate.find(query, User.class);
  }
}
