package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.User;

import java.util.List;

/**
 * Created by Alifa on 1/29/2016.
 */
public interface UserDao {
    List<User> getAllUser();
    User getUserById(Long id);
    User addNewUser(User user);
    User updateUser(User user);
}
