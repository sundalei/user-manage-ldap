package com.example.repository;

import com.example.entity.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findByCnAndSn(String cn, String sn);
}
