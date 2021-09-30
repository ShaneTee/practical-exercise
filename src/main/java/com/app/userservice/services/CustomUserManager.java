package com.app.userservice.services;

import com.app.userservice.components.requestBodies.User;
import com.app.userservice.exceptions.UserNotFoundException;

public interface CustomUserManager {
    void create(User user);

    User get(String email) throws UserNotFoundException;

    void delete(String email) throws UserNotFoundException;

    void update(String email, User user) throws UserNotFoundException;

}
