package com.example.repository.impl;

import com.example.entity.User;
import com.example.repository.UserRepositoryCustom;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;

import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final LdapTemplate ldapTemplate;

    public UserRepositoryImpl(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public List<User> findByCnAndSn(String cn, String sn) {
        LdapQuery query = LdapQueryBuilder.query()
                .where("objectclass").is("person")
                .and("cn").is(cn)
                .and("sn").is(sn);
        return ldapTemplate.find(query, User.class);
    }
}
