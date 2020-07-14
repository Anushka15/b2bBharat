package com.b2b.service;

import com.b2b.model.User;

public interface UserService {
    public User findUserByEmail(String email);

    public void saveUser(User user);
}
